package com.spbstu.curse.dto;

import com.spbstu.curse.domain.Person;
import com.spbstu.curse.domain.Subject;
import lombok.Data;

@Data
public class MarkDto {
  private Long id;
  private Person student;
  private Subject subject;
  private Person teacher;
  private Integer value;
}
