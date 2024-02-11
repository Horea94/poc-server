package com.example.app.security.filter;

import com.example.app.security.SecurityConstants;
import com.example.app.security.data.CustomUserDetails;
import com.example.app.security.data.AppUser;
import com.example.app.security.service.JavaWebTokenService;
import com.example.app.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JavaWebTokenService jwtService;

  final UserDetailsServiceImpl userDetailsServiceImpl;

  public JwtAuthFilter(JavaWebTokenService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
    this.jwtService = jwtService;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = null;
    String username = null;
    Cookie[] cookies = request.getCookies();
    String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER);
    if(cookies != null) {
      for(Cookie c : cookies) {
        if(c.getName().equals(SecurityConstants.MY_COOKIE)) {
          token = c.getValue();
          break;
        }
      }
    } else if (authHeader != null) {
      // TODO: in case of auth header, send only the token
      // Then remove the split
      String[] split = authHeader.split("=");
      if(split.length == 2) {
        token = split[1];
      }
    }

    if(token != null) {
      try {
        username = jwtService.extractUsername(token);
      } catch (Exception e) {
        logger.info(e.getMessage());
        Cookie c = new Cookie(SecurityConstants.MY_COOKIE, null);
        c.setDomain("localhost");
        c.setHttpOnly(true);
        c.setSecure(false);
        c.setPath("/");
        c.setMaxAge(0);
        response.addCookie(c);
      }
    }

    AppUser anon = AppUser.builder().username("anon").build();
    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
      if(jwtService.validateToken(token, userDetails)){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      } else {
        UserDetails userDetails1 = new CustomUserDetails(anon);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails1, null, userDetails1.getAuthorities()));
      }
    } else {
      UserDetails userDetails1 = new CustomUserDetails(anon);
      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails1, null, userDetails1.getAuthorities()));
    }

    filterChain.doFilter(request, response);
  }
}