package com.example.app.adlisting.data;

import com.example.app.base.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tmp_tests")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class TestEntity extends BaseEntity {
  @Column
  private String name;
}
