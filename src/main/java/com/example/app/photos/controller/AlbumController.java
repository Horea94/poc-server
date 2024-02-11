package com.example.app.photos.controller;

import com.example.app.base.controller.BaseController;
import com.example.app.photos.dto.AlbumDTO;
import com.example.app.photos.service.AlbumService;
import com.example.app.security.data.AppUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumController extends BaseController {
  private static final Logger log = LogManager.getLogger(AlbumController.class);
  private final AlbumService albumService;

  public AlbumController(AlbumService albumService) {
    this.albumService = albumService;
  }

  @PreAuthorize("hasAnyRole('PHOTOGRAPHER', 'ADMIN')")
  @PostMapping("/v1/createAlbum")
    public AlbumDTO createNewAlbum(@RequestBody String albumName) {
    AppUser appUser = getUserDetails();
    log.info(String.format("User %s is creating a new album with the name %s", appUser.getUsername(), albumName));
    return albumService.createAlbum(albumName, appUser);
  }
}
