package com.example.adlisting.security.service;

import com.example.adlisting.security.data.CustomUserDetails;
import com.example.adlisting.security.data.UserInfo;
import com.example.adlisting.security.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  private static final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    log.debug("Entering in loadUserByUsername Method...");
    UserInfo user = userRepository.findByUsername(username);
    if(user == null){
      log.error("Username not found: " + username);
      throw new UsernameNotFoundException("could not found user..!!");
    }
    log.info("User Authenticated Successfully..!!!");
    return new CustomUserDetails(user);
  }
}
