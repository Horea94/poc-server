package com.example.app.photos.data;

import com.example.app.base.data.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "image_resources")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"album"})
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"album"})
public class ImageResource extends BaseEntity {
  @Column
  private String extension;
  @Column
  private int fileSizesFlags;
  @Column
  private long originalImageSize; //bytes
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name="album_id", nullable=false)
  private Album album;
}

//path = ROOT_DIR/album.owner.uuid/album.uuid/
//basename = ROOT_DIR/album.owner.uuid/album.uuid/this.uuid
//full = ROOT_DIR/album.owner.uuid/album.uuid/this.uuid + ResizeType.SMALL.getName() + extension