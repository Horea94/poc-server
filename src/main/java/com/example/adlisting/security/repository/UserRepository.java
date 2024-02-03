package com.example.adlisting.security.repository;

import com.example.adlisting.security.data.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
  UserInfo findByUsername(String username);
}
