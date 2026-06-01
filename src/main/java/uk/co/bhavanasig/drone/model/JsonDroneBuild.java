package uk.co.bhavanasig.drone.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JsonDroneBuild {

  @JsonAlias("number")
  private Long buildNumber;
  private BuildStatus status;
  private String link;
  private BuildEvent event;
  private String title;
  @JsonAlias("source_repo")
  private String sourceRepo;
  @JsonAlias("source")
  private String sourceBranch;
  @JsonAlias("target")
  private String targetBranch;
  @JsonAlias("author_avatar")
  private String authorAvatar;
  @JsonAlias("sender")
  private String author;
  @JsonAlias("updated")
  private LocalDateTime lastUpdated;

  @JsonCreator
  public JsonDroneBuild() {
  }

  public Long getBuildNumber() {
    return buildNumber;
  }

  public JsonDroneBuild setBuildNumber(Long buildNumber) {
    this.buildNumber = buildNumber;
    return this;
  }

  public BuildStatus getStatus() {
    return status;
  }

  public JsonDroneBuild setStatus(BuildStatus status) {
    this.status = status;
    return this;
  }

  public String getLink() {
    return link;
  }

  public JsonDroneBuild setLink(String link) {
    this.link = link;
    return this;
  }

  public BuildEvent getEvent() {
    return event;
  }

  public JsonDroneBuild setEvent(BuildEvent event) {
    this.event = event;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public JsonDroneBuild setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getSourceRepo() {
    return sourceRepo;
  }

  public JsonDroneBuild setSourceRepo(String sourceRepo) {
    this.sourceRepo = sourceRepo;
    return this;
  }

  public String getSourceBranch() {
    return sourceBranch;
  }

  public JsonDroneBuild setSourceBranch(String sourceBranch) {
    this.sourceBranch = sourceBranch;
    return this;
  }

  public String getTargetBranch() {
    return targetBranch;
  }

  public JsonDroneBuild setTargetBranch(String targetBranch) {
    this.targetBranch = targetBranch;
    return this;
  }

  public String getAuthorAvatar() {
    return authorAvatar;
  }

  public JsonDroneBuild setAuthorAvatar(String authorAvatar) {
    this.authorAvatar = authorAvatar;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public JsonDroneBuild setAuthor(String author) {
    this.author = author;
    return this;
  }

//  public Long getLastUpdated() {
//    return lastUpdated;
//  }
//
//  public JsonDroneBuild setLastUpdated(Long lastUpdated) {
//    this.lastUpdated = lastUpdated;
//    return this;
//  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public JsonDroneBuild setLastUpdated(Long lastUpdated) {
    this.lastUpdated = LocalDateTime.ofEpochSecond(
        lastUpdated, 0,
        ZoneId.of("Europe/London").getRules().getOffset(Instant.now())
    );
    return this;
  }
}
