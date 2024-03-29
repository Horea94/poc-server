package com.example.app.adlisting.service;

import com.example.app.base.data.Status;
import com.example.app.adlisting.data.TestEntity;
import com.example.app.adlisting.repository.AdRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AdService {
  private static final Logger log = LogManager.getLogger(AdService.class);
  private final AdRepository adRepository;

  public AdService(AdRepository adRepository) {
    this.adRepository = adRepository;
  }

  public TestEntity getTestEntityById(String id) {
    log.info("Get by id: " + id);
    Optional<TestEntity> byId = this.adRepository.findById(id);
    return byId.orElse(null);
  }

  public List<TestEntity> getAllTestEntities() {
    log.info("Get all");
    return this.adRepository.findAll();
  }

  public TestEntity addTestEntity(TestEntity t) {
    log.info("add: " + t);
    return this.adRepository.save(t);
  }

  public TestEntity updateTestEntity(TestEntity t) {
    log.info("update: " + t);
    return this.adRepository.save(t);
  }

  public void deleteTestEntity(String id) {
    log.info("delete by id: " + id);
    this.adRepository.deleteById(id);
  }

  public void insertDummyTests() {
    Random r = new Random();
    for (int i = 0; i < 10; i++) {
      String name = "base_" + i;
      TestEntity e = TestEntity.builder().name(name).status(Status.ENABLED).build();
      this.adRepository.save(e);
    }
  }
}
