package com.example.adlisting.security.controller;

import com.example.adlisting.security.auth.CustomAuthenticationProvider;
import com.example.adlisting.security.data.AuthRequestDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private static final Logger log = LogManager.getLogger(AuthController.class);
  private final CustomAuthenticationProvider authenticationProvider;

  public AuthController(CustomAuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  @PostMapping("/v1/login")
  public void authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
    if(!authentication.isAuthenticated()){
      throw new UsernameNotFoundException("invalid user request..!!");
    }
  }
}
