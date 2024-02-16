package com.example.app.photos.service;

import com.example.app.base.data.Status;
import com.example.app.photos.data.Album;
import com.example.app.photos.data.ImageResource;
import com.example.app.photos.data.ResizeType;
import com.example.app.photos.dto.ImageResourceDTO;
import com.example.app.photos.repository.AlbumRepository;
import com.example.app.photos.repository.ImageResourceRepository;
import com.example.app.photos.util.ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItemInput;
import org.apache.commons.fileupload2.core.FileItemInputIterator;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.base.util.Constants.BASE_PATH;

@Service
public class FileService {
  private static final Logger log = LogManager.getLogger(FileService.class);
  private final ImageResourceRepository imageResourceRepository;
  private final AlbumRepository albumRepository;


  public FileService(ImageResourceRepository imageResourceRepository, AlbumRepository albumRepository) {
    this.imageResourceRepository = imageResourceRepository;
    this.albumRepository = albumRepository;
  }

  public void uploadFile(String userId, String albumId, HttpServletRequest request) {
    JakartaServletFileUpload<DiskFileItem, DiskFileItemFactory> servletFileUpload = new JakartaServletFileUpload<>();

    try {
      FileItemInputIterator itemIterator = servletFileUpload.getItemIterator(request);
      Album album = albumRepository.getReferenceById(albumId);
      String parentPath = BASE_PATH + userId + "\\" + albumId + "\\";
      File parentDir = new File(parentPath);
      if (!parentDir.exists() && !parentDir.mkdirs()) {
        log.info("Failed to create path: " + parentDir.getAbsolutePath());
      }
      while (itemIterator.hasNext()) {
        FileItemInput item = itemIterator.next();
        if (item.isFormField()) {
          continue;
        }
        String[] split = item.getName().strip().split("\\.");
        String extension = split[split.length - 1];
        ImageResource imageResource = imageResourceRepository.save(ImageResource.builder().album(album).extension(extension).status(Status.ENABLED).build());
        InputStream inputStream = item.getInputStream();
        long size = ImageUtil.saveImage(imageResource, inputStream);
        if(size > 0) {
          imageResource.setOriginalImageSize(size);
          ImageUtil.saveResizedCopiesOfImage(imageResource, ResizeType.getAllWidthTypes());
          imageResourceRepository.save(imageResource);
        } else {
          log.info(String.format("File size on disk is %d bytes!", size));
        }
      }
    } catch (Exception e) {
      log.error("Unable to save image to disk: " + e);
    }
  }

  public List<ImageResourceDTO> getImagesForAlbum(String albumId) {
    List<ImageResourceDTO> result = new ArrayList<>();
    Album album = albumRepository.getReferenceById(albumId);
    for(ImageResource imageResource : album.getImageResourceList()) {
      result.add(new ImageResourceDTO(imageResource.getId(), albumId, album.getOwner().getId(), imageResource.getExtension()));
    }
    return result;
  }

}
