package com.example.adlisting.security.repository;

import com.example.adlisting.security.data.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
  UserInfo findByUsername(String username);
}
