package com.example.app.photos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResourceDTO {
  private String id;
  private String albumUUID;
  private String userUUID;
  private String extension;
}
