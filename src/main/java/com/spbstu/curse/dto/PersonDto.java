package com.spbstu.curse.dto;

import com.spbstu.curse.domain.Group;
import com.spbstu.curse.domain.PersonRole;
import lombok.Data;

@Data
public class PersonDto {
  private Long id;
  private String firstname;
  private String lastname;
  private String pathername;
  private PersonRole role;
  private Group group;
}
