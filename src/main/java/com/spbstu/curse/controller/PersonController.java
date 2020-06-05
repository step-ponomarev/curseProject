package com.spbstu.curse.controller;

import com.spbstu.curse.dto.PersonDto;
import com.spbstu.curse.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
@RequiredArgsConstructor
public class PersonController {
  private final PersonService service;

  @GetMapping
  public ResponseEntity<List<PersonDto>> getAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<PersonDto> getPerson(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @DeleteMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public void deletePerson(@RequestBody PersonDto personDto) {
    service.delete(personDto);
  }

  @PutMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto) {
    return ResponseEntity.ok(service.update(personDto));
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public ResponseEntity<PersonDto> addPerson(@RequestBody PersonDto personDto) {
    return ResponseEntity.ok(service.save(personDto));
  }
}
