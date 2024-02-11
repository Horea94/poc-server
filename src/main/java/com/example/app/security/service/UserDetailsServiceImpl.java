package com.example.app.security.service;

import com.example.app.security.data.CustomUserDetails;
import com.example.app.security.data.AppUser;
import com.example.app.security.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;
  private static final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userRepository.findByUsername(username);
    if(user == null){
      log.error(String.format("Could not find user %s", username));
      throw new UsernameNotFoundException(String.format("Could not find user %s", username));
    }
    log.info(String.format("User %s authenticated", user));
    return new CustomUserDetails(user);
  }

  public AppUser loadAppUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userRepository.findByUsername(username);
    if(user == null){
      log.error(String.format("Could not find user %s", username));
      throw new UsernameNotFoundException(String.format("Could not find user %s", username));
    }
    log.info(String.format("User %s found", user));
    return user;
  }
}
