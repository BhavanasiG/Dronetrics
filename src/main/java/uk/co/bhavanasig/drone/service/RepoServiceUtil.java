package uk.co.bhavanasig.drone.service;

import java.time.Duration;
import java.util.Objects;
import uk.co.bhavanasig.drone.model.JsonDroneBuild;

public class RepoServiceUtil {

  public static final int REPOS_PAGE_SIZE = 20;

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

  public static Duration getRedisCacheTtl(JsonDroneBuild latestBuild) {
    if (Objects.isNull(latestBuild)) {
      return Duration.ZERO;
    }

    var buildStatus = latestBuild.getStatus();

    return switch (buildStatus) {
      case PENDING -> Duration.ofSeconds(5);
      case RUNNING, WAITING_ON_DEPENDENCIES -> Duration.ofSeconds(10);
      case BLOCKED -> Duration.ofSeconds(30);
      case SUCCESS, FAILURE, ERROR, KILLED, SKIPPED, DECLINED -> Duration.ofHours(12);
    };
  }
}
