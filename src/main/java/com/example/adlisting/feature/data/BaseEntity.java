package com.example.adlisting.feature.data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  protected String id;
  @Column
  @CreationTimestamp
  protected Timestamp created;
  @Column
  protected Status status;
}
