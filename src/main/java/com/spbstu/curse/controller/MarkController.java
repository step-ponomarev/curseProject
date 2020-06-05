package com.spbstu.curse.controller;

import com.spbstu.curse.dto.MarkDto;
import com.spbstu.curse.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mark")
@RequiredArgsConstructor
public class MarkController {
  private final MarkService service;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<MarkDto>> getMarks() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<MarkDto> getMark(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<MarkDto> addMark(@RequestBody MarkDto markDto) {
    return ResponseEntity.ok(service.save(markDto));
  }

  @DeleteMapping
  @PreAuthorize("isAuthenticated()")
  public void deleteMark(@RequestBody MarkDto markDto) {
    service.delete(markDto);
  }

  @PutMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<MarkDto> updateMark(@RequestBody MarkDto markDto) {
    return ResponseEntity.ok(service.put(markDto));
  }
}
