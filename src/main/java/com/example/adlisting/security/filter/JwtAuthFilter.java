package com.example.adlisting.security.filter;

import com.example.adlisting.security.SecurityConstants;
import com.example.adlisting.security.service.JwtService;
import com.example.adlisting.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  final UserDetailsServiceImpl userDetailsServiceImpl;

  public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
    this.jwtService = jwtService;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = null;
    String username = null;
    Cookie[] cookies = request.getCookies();
    if(cookies != null) {
      for(Cookie c : cookies) {
        if(c.getName().equals(SecurityConstants.MY_COOKIE)) {
          token = c.getValue();
          try {
            username = jwtService.extractUsername(token);
            break;
          } catch (Exception e) {
            logger.info(e.getMessage());
            username = null;
          }
        }
      }
    }

    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
      if(jwtService.validateToken(token, userDetails)){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }

    }

    filterChain.doFilter(request, response);
  }
}