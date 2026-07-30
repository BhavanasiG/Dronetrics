package uk.co.bhavanasig.drone.service;

import static uk.co.bhavanasig.drone.service.RepoServiceUtil.validateEnvs;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import uk.co.bhavanasig.drone.model.JsonDroneBuild;
import uk.co.bhavanasig.drone.model.JsonDroneRepo;

@Service
public class DroneApiEcaseRepoService {

  private final RestClient restClient;
  private final RedisTemplate<String, Object> redisCache;
//  private final Clock clock;

  public DroneApiEcaseRepoService(RestClient.Builder restClientBuilder,
                                  @Value("${drone.ecase.api.url}") String droneApiBaseUrl,
                                  @Value("${drone.ecase.api.bearer-token}") String bearerToken,
                                  @Value("${drone.api.connection-timeout-seconds}") int connectionTimeoutSeconds,
                                  RedisTemplate<String, Object> redisCache) {
    this.redisCache = redisCache;
    validateEnvs(droneApiBaseUrl, bearerToken, connectionTimeoutSeconds, true);
    var clientRequestFactory = new JdkClientHttpRequestFactory();
    clientRequestFactory.setReadTimeout(Duration.ofSeconds(connectionTimeoutSeconds));

    this.restClient = restClientBuilder
        .baseUrl(droneApiBaseUrl)
        .defaultHeader("Authorization", "Bearer " + bearerToken)
        .requestFactory(clientRequestFactory)
        .build();
  }

  public List<JsonDroneRepo> getDroneRepos() {
    return DroneApiServiceUtil.getDroneRepos(restClient);
  }

  public List<JsonDroneBuild> getDroneBuilds(String repoName, int page) {
    var redisKey = "drone-ecase-%s-%s".formatted(repoName, page);
//    List<JsonDroneBuild> cachedDroneBuilds = (List<JsonDroneBuild>) redisCache.opsForValue().get(redisKey);

//    if (CollectionUtils.isNotEmpty(cachedDroneBuilds)) {
//      return cachedDroneBuilds;
//    }

    var builds = Optional.ofNullable(restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/repos/%s/builds".formatted(repoName))
                .queryParam("per_page", 100)
                .queryParam("page", page)
                .build())
            .retrieve()
            .body(new ParameterizedTypeReference<List<JsonDroneBuild>>() {}))
        .orElse(new ArrayList<>()); // Keeping the explicit type for readability

    var latestBuild = builds.stream()
        .max(Comparator.comparing(JsonDroneBuild::getLastUpdated));


//    redisCache.opsForValue().set(redisKey, builds, Duration.ofSeconds(10)); // TODO adjust based on latest build

    return builds;
  }

//  private Duration setRedisCache(Optional<JsonDroneBuild> latestBuild) {
//    if (latestBuild.isEmpty()) {
//      return Duration.ZERO;
//    }
//
//
//
//    var lastUpdated = latestBuild.get().getLastUpdated();
//    var timeBetweenNowAndLastUpdated = ChronoUnit.HOURS.between(lastUpdated, LocalDate.now(clock));
//    return switch ((int) timeBetweenNowAndLastUpdated) {
//      case
//      case int m when m > 5 -> Duration.ofMinutes(5);
//      case int l when l > 2 -> Duration.ofSeconds(10);
//
//    };
//
//  }
}
