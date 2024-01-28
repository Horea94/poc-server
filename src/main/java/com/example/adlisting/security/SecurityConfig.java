package com.example.adlisting.security;

import com.example.adlisting.security.auth.MySimpleUrlAuthenticationSuccessHandler;
import com.example.adlisting.security.service.UserDetailsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  final UserDetailsServiceImpl userDetailsService;

  public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authReg -> authReg.requestMatchers("/v1/login").permitAll().anyRequest().authenticated())
        .formLogin(loginConfigurer -> loginConfigurer.successHandler(successHandler()))
        .logout(logoutConfigurer -> logoutConfigurer.deleteCookies("JSESSIONID"))
        .rememberMe(remember -> remember.key("uniqueAndSecret").tokenValiditySeconds(86400))
        .sessionManagement(configurer -> configurer.sessionFixation().
            migrateSession().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).
            maximumSessions(2));
    return http.build();

  }

  private AuthenticationSuccessHandler successHandler() {
    return new MySimpleUrlAuthenticationSuccessHandler();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
