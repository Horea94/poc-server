package com.example.app.security.auth;

import com.example.app.security.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
  private final UserDetailsServiceImpl userDetailsService;
  private final SimplePasswordEncoderDelegator passwordEncoder;

  public CustomAuthenticationProvider(UserDetailsServiceImpl userDetailsService, SimplePasswordEncoderDelegator passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (userDetails == null) {
      throw new UsernameNotFoundException("User not found");
    }
    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new AuthenticationException("Invalid credentials") { };
    }
    Authentication authenticated = new UsernamePasswordAuthenticationToken(
        userDetails, passwordEncoder.encode(password), userDetails.getAuthorities());
    return authenticated;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}