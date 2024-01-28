package com.example.adlisting.security.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String username;
  @JsonIgnore
  private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "app_users_roles",
      joinColumns = { @JoinColumn(name = "user_info_id") },
      inverseJoinColumns = { @JoinColumn(name = "roles_id") }
  )
  private Set<UserRole> roles = new HashSet<>();
}
