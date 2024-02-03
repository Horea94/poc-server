package com.example.adlisting.security.controller;

import com.example.adlisting.base.BaseController;
import com.example.adlisting.security.SecurityConstants;
import com.example.adlisting.security.auth.CustomAuthenticationProvider;
import com.example.adlisting.security.data.AuthRequestDTO;
import com.example.adlisting.security.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends BaseController {
  private static final Logger log = LogManager.getLogger(AuthController.class);
  private final JwtService jwtService;
  private final CustomAuthenticationProvider authenticationProvider;

  public AuthController(JwtService jwtService, CustomAuthenticationProvider authenticationProvider) {
    this.jwtService = jwtService;
    this.authenticationProvider = authenticationProvider;
  }

  @PostMapping("/v1/login")
  public void authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse res){

    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
    if(authentication.isAuthenticated()){
      String token = jwtService.GenerateToken(authRequestDTO.getUsername());
      Cookie c = new Cookie(SecurityConstants.MY_COOKIE, token);
      c.setSecure(false);
      c.setHttpOnly(true);
      res.addCookie(c);
    } else {
      throw new UsernameNotFoundException("invalid user request..!!");
    }
  }
}
