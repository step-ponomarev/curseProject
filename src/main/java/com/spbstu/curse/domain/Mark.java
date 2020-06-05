package com.spbstu.curse.domain;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
public class Mark {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Person student;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Person teacher;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Subject subject;

  @Column(nullable = false)
  private Integer value;
}
