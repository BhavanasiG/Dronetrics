package uk.co.bhavanasig.drone.service;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import uk.co.bhavanasig.drone.model.JsonDroneRepo;

public class DroneApiServiceUtil {

  private DroneApiServiceUtil() {
    throw new UnsupportedOperationException("This utility class cannot be instantiated");
  }

  public static List<JsonDroneRepo> getDroneRepos(RestClient restClient) {
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
}
