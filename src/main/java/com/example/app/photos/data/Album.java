package com.example.app.photos.data;

import com.example.app.base.data.BaseEntity;
import com.example.app.security.data.AppUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "albums")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"owner", "imageResourceList", "albumTemplate"})
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"owner", "imageResourceList", "albumTemplate"})
public class Album extends BaseEntity {
  @Column
  private String name;
  @Column
  private String accessPin;
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private AppUser owner;
  @OneToMany(mappedBy = "album")
  private Set<ImageResource> imageResourceList;
  @ManyToOne
  @JoinColumn(name="album_template_id", nullable=true)
  private AlbumTemplate albumTemplate;
}
