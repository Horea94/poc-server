package com.example.app.photos.repository;

import com.example.app.photos.data.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, String> {
}
