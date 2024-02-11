package com.example.app.security.controller;

import com.example.app.base.controller.BaseController;
import com.example.app.security.SecurityConstants;
import com.example.app.security.auth.CustomAuthenticationProvider;
import com.example.app.security.data.AuthRequestDTO;
import com.example.app.security.service.JavaWebTokenService;
import com.example.app.security.service.UserDetailsServiceImpl;
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
  private final JavaWebTokenService jwtService;
  private final CustomAuthenticationProvider authenticationProvider;

  public AuthController(JavaWebTokenService jwtService, CustomAuthenticationProvider authenticationProvider) {
    this.jwtService = jwtService;
    this.authenticationProvider = authenticationProvider;
  }

  @PostMapping("/v1/login")
  public void authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse res){

    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
    if(authentication.isAuthenticated()){
      String token = jwtService.GenerateToken(authRequestDTO.getUsername());
      Cookie c = new Cookie(SecurityConstants.MY_COOKIE, token);
      c.setDomain("localhost");
      c.setHttpOnly(true);
      c.setSecure(false);
      c.setPath("/");
      c.setMaxAge(60 * 60 * 24 * 7);
      res.addCookie(c);
    } else {
      throw new UsernameNotFoundException("invalid user request..!!");
    }
  }
}
