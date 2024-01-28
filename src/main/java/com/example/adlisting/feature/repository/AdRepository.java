package com.example.adlisting.feature.repository;

import com.example.adlisting.feature.data.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<TestEntity, Long> {

}
