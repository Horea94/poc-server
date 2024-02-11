package com.example.app.security.controller;

import com.example.app.base.controller.BaseController;
import com.example.app.security.data.AppUser;
import com.example.app.security.service.UserCRUDServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController extends BaseController {
  private static final Logger log = LogManager.getLogger(AdminController.class);
  private final UserCRUDServiceImpl userCRUDService;

  public AdminController(UserCRUDServiceImpl userCRUDService) {
    this.userCRUDService = userCRUDService;
  }

  @PostMapping("/v1/createUser")
  public AppUser createUser(@RequestBody AppUser appUser) {
    log.info("create user: " + appUser);
    return userCRUDService.createUser(appUser);
  }

  @PutMapping("/v1/updateUser")
  public AppUser updateUser(@RequestBody AppUser appUser) {
    log.info("update user: " + appUser);
    return userCRUDService.updateUser(appUser);
  }

  @DeleteMapping("/v1/deleteUser")
  public void deleteUser(@RequestBody String uid) {
    log.info("delete user by id: " + uid);
    userCRUDService.deleteUser(uid);
  }

}