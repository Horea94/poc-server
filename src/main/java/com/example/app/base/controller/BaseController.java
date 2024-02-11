package com.example.app.base.controller;

import com.example.app.security.data.AppUser;
import com.example.app.security.service.UserDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
  private static final Logger log = LogManager.getLogger(BaseController.class);

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  protected String getUsernameFromContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    log.info(name);
    return name;
  }

  protected AppUser getUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    AppUser user = userDetailsService.loadAppUserByUsername(name);
    log.info(String.format("Getting details for user %s", user));
    return user;
  }
}
