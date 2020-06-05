package com.spbstu.curse.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "crew")
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;
}
