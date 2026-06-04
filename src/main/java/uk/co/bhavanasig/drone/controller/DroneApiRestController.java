package uk.co.bhavanasig.drone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.bhavanasig.drone.service.DroneApiDigitalRepoService;
import uk.co.bhavanasig.drone.service.DroneApiEcaseRepoService;

@RestController
@RequestMapping("/rest/api/drone")
public class DroneApiRestController {
  private final DroneApiEcaseRepoService droneApiEcaseRepoService;
  private final DroneApiDigitalRepoService droneApiDigitalRepoService;

  @Autowired
  public DroneApiRestController(DroneApiEcaseRepoService droneApiEcaseRepoService,
                                DroneApiDigitalRepoService droneApiDigitalRepoService) {
    this.droneApiEcaseRepoService = droneApiEcaseRepoService;
    this.droneApiDigitalRepoService = droneApiDigitalRepoService;
  }

  @GetMapping("/ecase/repos")
  public ResponseEntity<?> getEcaseDroneApiData() {
    return ResponseEntity.ok(droneApiEcaseRepoService.getDroneRepos());
  }

  @GetMapping("/digital/repos")
  public ResponseEntity<?> getDigitalDroneApiData() {
    return ResponseEntity.ok(droneApiDigitalRepoService.getDroneRepos());
  }

  @GetMapping("/ecase/builds")
  public ResponseEntity<?> getEcaseDroneApiData(@RequestParam String repoName) {
    return ResponseEntity.ok(droneApiEcaseRepoService.getDroneBuilds(repoName));
  }

  @GetMapping("/digital/builds")
  public ResponseEntity<?> getDigitalDroneApiData(@RequestParam String repoName) {
    return ResponseEntity.ok(droneApiDigitalRepoService.getDroneBuilds(repoName));
  }
}
