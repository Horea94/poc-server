package com.example.adlisting.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
  private static final Logger log = LogManager.getLogger(BaseController.class);

  protected String getUsernameFromContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    log.info(name);
    return name;
  }
}
