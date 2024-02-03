package com.example.adlisting.security.data;

import com.example.adlisting.feature.data.BaseEntity;
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
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class UserInfo extends BaseEntity {
  @Column
  private String username;

  @Column
  @JsonIgnore
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "app_users_roles",
      joinColumns = { @JoinColumn(name = "user_info_id") },
      inverseJoinColumns = { @JoinColumn(name = "roles_id") }
  )
  @Builder.Default
  private Set<UserRole> roles = new HashSet<>();
}
