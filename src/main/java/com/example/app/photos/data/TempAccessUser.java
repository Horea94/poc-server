package com.example.app.photos.data;

import com.example.app.base.data.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "temp_user")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class TempAccessUser extends BaseEntity {
  @Column
  private String email;
  @ManyToOne
  private Album album;
}
