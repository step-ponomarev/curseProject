package com.spbstu.curse.domain;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "people")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String lastname;

  @Column(nullable = false)
  private String pathername;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PersonRole role;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Group group;
}
