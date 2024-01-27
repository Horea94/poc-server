package com.example.adlisting.controller;

import com.example.adlisting.data.TestEntity;
import com.example.adlisting.service.AdService;
import com.example.adlisting.websocket.WebsocketConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdController {
  private static final Logger log = LogManager.getLogger(AdController.class);
  private final AdService adService;
  private final WebsocketConnection websocketConnection;

  public AdController(AdService adService, WebsocketConnection websocketConnection) {
    this.adService = adService;
    this.websocketConnection = websocketConnection;
  }

  @GetMapping("/tests")
  public List<TestEntity> getTests() {
    log.info("get all entities");
    return adService.getAllTestEntities();
  }

  @GetMapping("/test/{id}")
  public TestEntity getTestById(@PathVariable long id) {
    log.info("get bty id: " + id);
    return adService.getTestEntityById(id);
  }
}
