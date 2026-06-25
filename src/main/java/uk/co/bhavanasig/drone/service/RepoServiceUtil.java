package uk.co.bhavanasig.drone.service;

public class RepoServiceUtil {

  private RepoServiceUtil() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void validateEnvs(String droneApiBaseUrl, String bearerToken, int connectionTimeoutSeconds, boolean isEcase) {
    if (droneApiBaseUrl == null || droneApiBaseUrl.isBlank()) {
      throw new IllegalArgumentException("Property 'drone.%s.api.url' must be not be null or blank"
          .formatted(isEcase ? "ecase" : "digital"));
    }

    if (bearerToken == null || bearerToken.isBlank()) {
      throw new IllegalArgumentException("Property 'drone.%s.api.bearer-token' must be not be null or blank"
          .formatted(isEcase ? "ecase" : "digital"));
    }

    if (connectionTimeoutSeconds <= 0) {
      throw new IllegalArgumentException("Property 'drone.api.connection-timeout-seconds' must be larger than 0 seconds");
    }
  }
}
