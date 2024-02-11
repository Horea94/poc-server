package com.example.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

  private static final Logger log = LogManager.getLogger(MainApplication.class);
  public static void main(String[] args) {
    log.info("----------------- STARTING -----------------");
    SpringApplication.run(MainApplication.class, args);
  }

}
