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
  public ResponseEntity<?> getEcaseRepos() {
    return ResponseEntity.ok(droneApiEcaseRepoService.getDroneRepos());
  }

  @GetMapping("/ecase/builds")
  public ResponseEntity<?> getEcaseBuilds(@RequestParam String repoName, @RequestParam(defaultValue = "1") Integer page) {
    return ResponseEntity.ok(droneApiEcaseRepoService.getDroneBuilds(repoName, page));
  }

  @GetMapping("/digital/repos")
  public ResponseEntity<?> getDigitalRepos() {
    return ResponseEntity.ok(droneApiDigitalRepoService.getDroneRepos());
  }

  @GetMapping("/digital/builds")
  public ResponseEntity<?> getDigitalBuilds(@RequestParam String repoName, @RequestParam(defaultValue = "1") Integer page) {
    return ResponseEntity.ok(droneApiDigitalRepoService.getDroneBuilds(repoName, page));
  }
}
