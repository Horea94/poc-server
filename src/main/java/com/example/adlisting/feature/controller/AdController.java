package com.example.adlisting.feature.controller;

import com.example.adlisting.feature.data.TestEntity;
import com.example.adlisting.feature.service.AdService;
import com.example.adlisting.feature.websocket.WebsocketConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/v1/tests")
  public List<TestEntity> getTests() {
    log.info("get all entities");
    return adService.getAllTestEntities();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping("/v1/dummy_tests")
  public void insertDummyTests() {
    log.info("add dummy data");
    adService.insertDummyTests();
  }

  @GetMapping("/v1/test/{id}")
  public TestEntity getTestById(@PathVariable String id) {
    log.info("get by id: " + id);
    return adService.getTestEntityById(id);
  }
}
