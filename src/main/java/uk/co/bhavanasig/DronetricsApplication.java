package uk.co.bhavanasig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DronetricsApplication {

  static void main(String[] args) {
    SpringApplication.run(DronetricsApplication.class, args);
  }

}
