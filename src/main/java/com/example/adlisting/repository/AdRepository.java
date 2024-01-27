package com.example.adlisting.repository;

import com.example.adlisting.data.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<TestEntity, Long> {

}
