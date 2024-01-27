package com.example.adlisting.data;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;

@Entity
@Table(name = "tmp_tests")
@SQLRestriction("id > 2")
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
