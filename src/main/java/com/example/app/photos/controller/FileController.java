package com.example.app.photos.controller;

import com.example.app.base.controller.BaseController;
import com.example.app.photos.dto.ImageResourceDTO;
import com.example.app.photos.service.FileService;
import com.example.app.security.data.AppUser;
import com.example.app.security.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController extends BaseController {
  private static final Logger log = LogManager.getLogger(FileController.class);

  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PreAuthorize("hasAnyRole('PHOTOGRAPHER', 'ADMIN')")
  @PostMapping("/v1/uploadImage/{albumId}")
  public void upload(@PathVariable String albumId, HttpServletRequest request) {
    AppUser appUser = getUserDetails();
    log.info(String.format("User %s is uploading image to album %s", appUser.getUsername(), albumId));
    fileService.uploadFile(appUser.getId(), albumId, request);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/v1/getImages/{albumId}")
  public List<ImageResourceDTO> getImagesForAlbum(@PathVariable String albumId) {
    log.info(String.format("Getting images for album %s", albumId));
    return fileService.getImagesForAlbum(albumId);
  }
}
