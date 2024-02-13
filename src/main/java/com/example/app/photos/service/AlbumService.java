package com.example.app.photos.service;

import com.example.app.base.data.Status;
import com.example.app.photos.data.Album;
import com.example.app.photos.data.AlbumTemplate;
import com.example.app.photos.dto.AlbumDTO;
import com.example.app.photos.repository.AlbumRepository;
import com.example.app.security.data.AppUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
  private static final Logger log = LogManager.getLogger(AlbumService.class);
  private final AlbumRepository albumRepository;

  public AlbumService(AlbumRepository albumRepository) {
    this.albumRepository = albumRepository;
  }

  public AlbumDTO createAlbum(String albumName, AppUser appUser) {
    Album album = Album.builder().name(albumName).owner(appUser).status(Status.ENABLED).build();
    Album save = albumRepository.save(album);
    log.info(String.format("Created album: %s", album));
    AlbumTemplate albumTemplate = album.getAlbumTemplate();
    String templateName = "";
    if(albumTemplate != null)
      templateName = albumTemplate.getName();
    return new AlbumDTO(save.getId(), save.getName(), templateName);
  }
}
