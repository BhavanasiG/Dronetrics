package uk.co.bhavanasig.drone.service;

import static uk.co.bhavanasig.drone.service.RepoServiceUtil.validateEnvs;

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
public class DroneApiDigitalRepoService {

  private final RestClient restClient;

  public DroneApiDigitalRepoService(RestClient.Builder restClientBuilder,
                                    @Value("${drone.digital.api.url}") String droneApiBaseUrl,
                                    @Value("${drone.digital.api.bearer-token}") String bearerToken,
                                    @Value("${drone.api.connection-timeout-seconds}") int connectionTimeoutSeconds) {
    validateEnvs(droneApiBaseUrl, bearerToken, connectionTimeoutSeconds, false);
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
    var builds = restClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/repos/%s/builds".formatted(repoName))
            .queryParam("per_page", 100)
            .queryParam("page", page)
            .build())
        .retrieve()
        .body(new ParameterizedTypeReference<List<JsonDroneBuild>>() {}); // Keeping the explicit type for readability

    return builds == null ? List.of() : builds;
  }
}
