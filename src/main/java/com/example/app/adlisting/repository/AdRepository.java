package com.example.app.adlisting.repository;

import com.example.app.adlisting.data.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<TestEntity, String> {

}
