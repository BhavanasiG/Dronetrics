package uk.co.bhavanasig.drone.model;

import java.io.Serializable;
import java.util.Objects;

public class JsonDroneBuildId implements Serializable {
  private String repoName;
  private Long buildNumber;

  public JsonDroneBuildId() {
  }

  public JsonDroneBuildId(String repoName, Long buildNumber) {
    this.repoName = repoName;
    this.buildNumber = buildNumber;
  }

  public String getRepoName() {
    return repoName;
  }

  public JsonDroneBuildId setRepoName(String repoName) {
    this.repoName = repoName;
    return this;
  }

  public Long getBuildNumber() {
    return buildNumber;
  }

  public JsonDroneBuildId setBuildNumber(Long buildNumber) {
    this.buildNumber = buildNumber;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonDroneBuildId that = (JsonDroneBuildId) o;
    return Objects.equals(repoName, that.repoName) && Objects.equals(buildNumber, that.buildNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repoName, buildNumber);
  }
}
