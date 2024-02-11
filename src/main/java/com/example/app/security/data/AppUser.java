package com.example.app.security.data;

import com.example.app.base.data.BaseEntity;
import com.example.app.photos.data.Album;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"albums"})
@SuperBuilder
@NoArgsConstructor
public class AppUser extends BaseEntity {
  @Column
  protected String username;

  @Column
  @JsonIgnore
  protected String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "app_users_roles",
      joinColumns = { @JoinColumn(name = "app_user_id") },
      inverseJoinColumns = { @JoinColumn(name = "roles_id") }
  )
  @Builder.Default
  private Set<UserRole> roles = new HashSet<>();

  @OneToMany(mappedBy = "owner")
  private Set<Album> albums;
}
