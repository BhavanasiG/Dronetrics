package uk.co.bhavanasig.drone.controller;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

/**
 * TODO: Implement remaining APIs for getting data from Drone API
 */
@RestController
@RequestMapping("/rest/api/drone")
public class DroneApiRestController {
  private final RestClient restClient;

  @Autowired
  public DroneApiRestController(@Value("${drone.api.url}") String droneApiBaseUrl,
                                @Value("${drone.api.bearer-token}") String bearerToken,
                                @Value("${drone.api.connection-timeout-seconds}") int connectionTimeoutSeconds) {
    var clientRequestFactory = new JdkClientHttpRequestFactory();
    clientRequestFactory.setReadTimeout(Duration.ofSeconds(connectionTimeoutSeconds));

    this.restClient = RestClient.builder()
        .baseUrl(droneApiBaseUrl)
        .defaultHeader("Authorization", "Bearer " + bearerToken)
        .requestFactory(clientRequestFactory)
        .build();
  }

  @GetMapping
  // TODO: implement the JsonDroneApiResponse object to put into the response entity
  public ResponseEntity<?> getDroneApiData() {
    var response = restClient.get()
        .uri("/api/user/repos")
        .retrieve()
        .toEntity(String.class);

    return ResponseEntity.ok(response.getBody());
  }


}
