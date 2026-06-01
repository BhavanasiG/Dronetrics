package uk.co.bhavanasig.drone.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JsonDroneRepo {

  @JsonAlias("slug")
  private String repoName;
  private JsonDroneBuild build;

  public JsonDroneRepo() {
  }

  public String getRepoName() {
    return repoName;
  }

  public JsonDroneRepo setRepoName(String repoName) {
    this.repoName = repoName;
    return this;
  }

  public JsonDroneBuild getBuild() {
    return build;
  }

  public JsonDroneRepo setBuild(JsonDroneBuild build) {
    this.build = build;
    return this;
  }
}
