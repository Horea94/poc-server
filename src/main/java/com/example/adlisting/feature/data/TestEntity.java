package com.example.adlisting.feature.data;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "tmp_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column
  private String name;
  @Column
  private float price;
}
