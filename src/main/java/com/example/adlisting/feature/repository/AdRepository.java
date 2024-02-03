package com.example.adlisting.feature.repository;

import com.example.adlisting.feature.data.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdRepository extends JpaRepository<TestEntity, String> {

}
