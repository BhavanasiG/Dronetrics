package uk.co.bhavanasig.drone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DroneOldBuildCleanupService {

  private static final Logger log = LoggerFactory.getLogger(DroneOldBuildCleanupService.class);
  private final JdbcTemplate jdbcTemplate;

  public DroneOldBuildCleanupService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  // Runs every day at 2:00 AM server time (webapp unlikely to be queried then)
  @Scheduled(cron = "0 0 2 * * *")
  public void purgeOldDroneBuilds() {
    log.info("Starting scheduled cleanup of old drone builds...");

    String deleteSql = "DELETE FROM dronetrics.json_drone_builds WHERE last_updated < NOW() - INTERVAL '20 days'";
    int deletedRows = jdbcTemplate.update(deleteSql);

    log.info("Successfully deleted {} builds older than 20 days.", deletedRows);
  }
}
