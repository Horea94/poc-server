package com.example.app.security.service;

import com.example.app.security.data.AppUser;
import com.example.app.security.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserCRUDServiceImpl {
  private static final Logger log = LogManager.getLogger(UserCRUDServiceImpl.class);
  private final UserRepository userRepository;

  public UserCRUDServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public AppUser createUser(AppUser appUser) {
    log.info("create user: " + appUser);
    return userRepository.save(appUser);
  }

  public void deleteUser(String uid) {
    log.info("delete user: " + uid);
    userRepository.deleteById(uid);
  }

  public AppUser updateUser(AppUser appUser) {
    log.info("update user: " + appUser);
    return userRepository.save(appUser);
  }
}
