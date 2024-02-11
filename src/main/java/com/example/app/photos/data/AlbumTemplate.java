package com.example.app.photos.data;

import com.example.app.base.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "album_templates")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"albums"})
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"albums"})
public class AlbumTemplate extends BaseEntity {
  @Column
  private String name;

  @OneToMany(mappedBy = "albumTemplate")
  private Set<Album> albums;
}
