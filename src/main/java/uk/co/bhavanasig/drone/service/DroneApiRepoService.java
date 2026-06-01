package uk.co.bhavanasig.drone.service;

import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import uk.co.bhavanasig.drone.model.JsonDroneBuild;
import uk.co.bhavanasig.drone.model.JsonDroneRepo;

@Service
public class DroneApiRepoService {

  private final RestClient restClient;

  public DroneApiRepoService(RestClient.Builder restClientBuilder,
                             @Value("${drone.api.url}") String droneApiBaseUrl,
                             @Value("${drone.api.bearer-token}") String bearerToken,
                             @Value("${drone.api.connection-timeout-seconds}") int connectionTimeoutSeconds) {
    var clientRequestFactory = new JdkClientHttpRequestFactory();
    clientRequestFactory.setReadTimeout(Duration.ofSeconds(connectionTimeoutSeconds));

    this.restClient = restClientBuilder
        .baseUrl(droneApiBaseUrl)
        .defaultHeader("Authorization", "Bearer " + bearerToken)
        .requestFactory(clientRequestFactory)
        .build();
  }

  public List<JsonDroneRepo> getDroneRepos() {
    var repos = restClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/user/repos")
            .queryParam("latest", true)
            .build())
        .retrieve()
        .body(new ParameterizedTypeReference<List<JsonDroneRepo>>() {});

    if (repos == null) {
      return List.of();
    }

    return repos.stream()
        .filter(repo -> repo.getBuild() != null)
        .toList();
  }

  public List<JsonDroneBuild> getDroneBuilds(String repoName) {
    return restClient.get()
        .uri("/api/repos/%s/builds".formatted(repoName))
        .retrieve()
        .body(new ParameterizedTypeReference<List<JsonDroneBuild>>() {});
  }
}
