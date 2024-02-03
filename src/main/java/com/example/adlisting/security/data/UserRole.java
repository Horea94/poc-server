package com.example.adlisting.security.data;

import com.example.adlisting.feature.data.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "roles")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class UserRole extends BaseEntity {
  @Column
  private String name;
}