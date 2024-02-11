package com.example.app.photos.repository;

import com.example.app.photos.data.ImageResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageResourceRepository extends JpaRepository<ImageResource, String> {
}
