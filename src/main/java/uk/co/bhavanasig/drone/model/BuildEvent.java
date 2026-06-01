package uk.co.bhavanasig.drone.model;

public enum BuildEvent {
  PUSH,
  PULL_REQUEST,
  TAG,
  PROMOTE,
  ROLLBACK,
  CRON,
  CUSTOM,
}
