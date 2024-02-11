package com.example.app.security.repository;

import com.example.app.security.data.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<AppUser, String> {
  AppUser findByUsername(String username);
}
