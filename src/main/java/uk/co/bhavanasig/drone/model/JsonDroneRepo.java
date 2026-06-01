package uk.co.bhavanasig.drone.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JsonDroneRepo {

  private String name;
  @JsonAlias("slug")
  private String fullName;
  @JsonAlias("version")
  private Long buildNumber;
  //  @JsonAlias("build")
  private JsonDroneBuild build;

  public JsonDroneRepo() {
  }

  public String getName() {
    return name;
  }

  public JsonDroneRepo setName(String name) {
    this.name = name;
    return this;
  }

  public String getFullName() {
    return fullName;
  }

  public JsonDroneRepo setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public Long getBuildNumber() {
    return buildNumber;
  }

  public JsonDroneRepo setBuildNumber(Long buildNumber) {
    this.buildNumber = buildNumber;
    return this;
  }

  public JsonDroneBuild getBuild() {
    return build;
  }

  public JsonDroneRepo setBuild(JsonDroneBuild build) {
    this.build = build;
    return this;
  }

  //  public JsonDroneBuild getLatestBuild() {
//    return latestBuild;
//  }
//
//  public JsonDroneRepo setLatestBuild(JsonDroneBuild latestBuild) {
//    this.latestBuild = latestBuild;
//    return this;
//  }
//
//  private void unpackNested(Map<String, Object> build) {
//    this.latestBuild = (String) brand.get("name");
//    Map<String, String> owner = (Map<String, String>) brand.get("owner");
//    this.ownerName = owner.get("name");
//  }
}
