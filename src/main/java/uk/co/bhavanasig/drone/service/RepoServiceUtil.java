package uk.co.bhavanasig.drone.service;

public class RepoServiceUtil {

  private RepoServiceUtil() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void validateEnvs(String droneApiBaseUrl, String bearerToken, int connectionTimeoutSeconds) {
    if (droneApiBaseUrl == null || droneApiBaseUrl.isBlank()) {
      throw new IllegalArgumentException("Property 'drone.ecase.api.url' must be not be null or blank");
    }

    if (bearerToken == null || bearerToken.isBlank()) {
      throw new IllegalArgumentException("Property 'drone.ecase.api.url' must be not be null or blank");
    }

    if (connectionTimeoutSeconds <= 0) {
      throw new IllegalArgumentException("Property 'drone.ecase.api.url' must be larger than 0 seconds");
    }
  }
}
