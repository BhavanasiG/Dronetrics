package uk.co.bhavanasig.drone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.bhavanasig.drone.service.DroneApiRepoService;

/**
 * TODO: Implement remaining APIs for getting data from Drone API
 */
@RestController
@RequestMapping("/rest/api/drone")
public class DroneApiRestController {
  private final DroneApiRepoService droneApiRepoService;

  @Autowired
  public DroneApiRestController(DroneApiRepoService droneApiRepoService) {
    this.droneApiRepoService = droneApiRepoService;
  }

  @GetMapping("/repos")
  public ResponseEntity<?> getDroneApiData() {
    return ResponseEntity.ok(droneApiRepoService.getDroneRepos());
  }

  @GetMapping("/builds")
  public ResponseEntity<?> getDroneApiData(@RequestParam String repoName) {
    System.out.println("Hwllo");
    return ResponseEntity.ok(droneApiRepoService.getDroneBuilds(repoName));
  }
}
