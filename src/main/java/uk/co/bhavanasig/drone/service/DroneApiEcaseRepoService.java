package uk.co.bhavanasig.drone.service;

import static uk.co.bhavanasig.drone.model.BuildStatus.PENDING;
import static uk.co.bhavanasig.drone.model.BuildStatus.RUNNING;
import static uk.co.bhavanasig.drone.service.RepoServiceUtil.REPOS_PAGE_SIZE;
import static uk.co.bhavanasig.drone.service.RepoServiceUtil.getRedisCacheTtl;
import static uk.co.bhavanasig.drone.service.RepoServiceUtil.validateEnvs;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import uk.co.bhavanasig.drone.model.JsonDroneBuild;
import uk.co.bhavanasig.drone.model.JsonDroneRepo;
import uk.co.bhavanasig.drone.repo.JsonDroneBuildRepository;

@Service
public class DroneApiEcaseRepoService {

  private final RestClient restClient;
  private final RedisTemplate<String, Object> redisCache;
  private final JsonDroneBuildRepository jsonDroneBuildRepository;

  @Value("${drone.longCacheStartPage:5}")
  private int longCacheStartPage;

  public DroneApiEcaseRepoService(RestClient.Builder restClientBuilder,
                                  @Value("${drone.ecase.api.url}") String droneApiBaseUrl,
                                  @Value("${drone.ecase.api.bearer-token}") String bearerToken,
                                  @Value("${drone.api.connection-timeout-seconds}") int connectionTimeoutSeconds,
                                  RedisTemplate<String, Object> redisCache, JsonDroneBuildRepository jsonDroneBuildRepository) {
    this.redisCache = redisCache;
    validateEnvs(droneApiBaseUrl, bearerToken, connectionTimeoutSeconds, true);
    var clientRequestFactory = new JdkClientHttpRequestFactory();
    clientRequestFactory.setReadTimeout(Duration.ofSeconds(connectionTimeoutSeconds));

    this.restClient = restClientBuilder
        .baseUrl(droneApiBaseUrl)
        .defaultHeader("Authorization", "Bearer " + bearerToken)
        .requestFactory(clientRequestFactory)
        .build();
    this.jsonDroneBuildRepository = jsonDroneBuildRepository;
  }

  public List<JsonDroneRepo> getDroneRepos() {
    return DroneApiServiceUtil.getDroneRepos(restClient);
  }

  public List<JsonDroneBuild> getDroneBuilds(String repoName, int page) {
    var redisKey = "drone-ecase-%s-%s".formatted(repoName, page);
    if (page <= longCacheStartPage) {
      List<JsonDroneBuild> cachedDroneBuilds = (List<JsonDroneBuild>) redisCache.opsForValue().get(redisKey);

      if (CollectionUtils.isNotEmpty(cachedDroneBuilds)) {
        return cachedDroneBuilds;
      }
    }

    if (page > longCacheStartPage) {
      List<JsonDroneBuild> longCachedDroneBuilds = jsonDroneBuildRepository.findByRepoName(
          repoName,
          PageRequest.of(
              page - 1, REPOS_PAGE_SIZE,
              Sort.by(Sort.Direction.DESC, "buildNumber")
          )
      ).toList();

      if (CollectionUtils.isNotEmpty(longCachedDroneBuilds)) {
        return longCachedDroneBuilds;
      }
    }

    var builds = Optional.ofNullable(restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/repos/%s/builds".formatted(repoName))
                .queryParam("per_page", REPOS_PAGE_SIZE)
                .queryParam("page", page)
                .build())
            .retrieve()
            .body(new ParameterizedTypeReference<List<JsonDroneBuild>>() {}))
        .orElse(new ArrayList<>()); // Keeping the explicit type for readability

    var latestBuild = builds.stream()
        .max(Comparator.comparing(JsonDroneBuild::getLastUpdated));

    if (page <= longCacheStartPage) {
      redisCache.opsForValue().set(redisKey, builds, getRedisCacheTtl(latestBuild.orElse(null)));
    }

    builds.forEach(build -> build.setRepoName(repoName));

    var completedBuilds = builds.stream()
        .filter(build -> build.getStatus() != PENDING)
        .filter(build -> build.getStatus() != RUNNING)
        .map(build -> build.setRepoName(repoName))
        .toList();

    if (!completedBuilds.isEmpty()) {
      jsonDroneBuildRepository.saveAll(completedBuilds);
    }

    return builds;
  }
}
