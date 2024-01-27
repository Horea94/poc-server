package com.example.adlisting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdListingApplication {

  private static final Logger log = LogManager.getLogger(AdListingApplication.class);
  public static void main(String[] args) {
    log.info("----------------- STARTING -----------------");
    SpringApplication.run(AdListingApplication.class, args);
  }

}
