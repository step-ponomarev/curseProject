package com.spbstu.curse.controller;

import com.spbstu.curse.dto.SubjectDto;
import com.spbstu.curse.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("subject")
@RequiredArgsConstructor
public class SubjectController {
  private final SubjectService service;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<SubjectDto>> getSubjects() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<SubjectDto> getSubject(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<SubjectDto> addSubject(@RequestBody SubjectDto subjectDto) {
    return ResponseEntity.ok(service.save(subjectDto));
  }

  @PutMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<SubjectDto> putSubject(@RequestBody SubjectDto subjectDto) {
    return ResponseEntity.ok(service.put(subjectDto));
  }

  @DeleteMapping
  public void deleteSubject(@RequestBody SubjectDto subjectDto) {
    service.delete(subjectDto);
  }
}
