package com.example.app.adlisting.controller;

import com.example.app.base.controller.BaseController;
import com.example.app.adlisting.data.TestEntity;
import com.example.app.adlisting.service.AdService;
import com.example.app.base.websocket.WebsocketConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class AdController extends BaseController {
  private static final Logger log = LogManager.getLogger(AdController.class);
  private final AdService adService;
  private final WebsocketConnection websocketConnection;

  public AdController(AdService adService, WebsocketConnection websocketConnection) {
    this.adService = adService;
    this.websocketConnection = websocketConnection;
  }

//  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/v1/tests")
  public List<TestEntity> getTests() {
    log.info("get all entities");

    List<TestEntity> allTestEntities = adService.getAllTestEntities();
    for(TestEntity te : allTestEntities) {
      log.info("Entity: " + te);
    }
    if("anon".equals(getUsernameFromContext())) {
      allTestEntities = allTestEntities.stream().filter(e -> e.getName().matches("^.*[02468]$")).collect(Collectors.toList());
    }
    return allTestEntities;
  }

//  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping("/v1/dummy_tests")
  public void insertDummyTests() {
    log.info("add dummy data");
    adService.insertDummyTests();
  }

  @GetMapping("/v1/test/{id}")
  public TestEntity getTestById(@PathVariable String id) {
    log.info("user: " + getUsernameFromContext() + " executes get by id: " + id);
    return adService.getTestEntityById(id);
  }
}
