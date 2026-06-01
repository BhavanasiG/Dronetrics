package uk.co.bhavanasig.drone.model;

public enum BuildStatus {
  PENDING,
  RUNNING,
  SUCCESS,
  FAILURE,
  KILLED,
  ERROR,
  SKIPPED,
  BLOCKED,
  DECLINED,
  WAITING_ON_DEPENDENCIES,
}
