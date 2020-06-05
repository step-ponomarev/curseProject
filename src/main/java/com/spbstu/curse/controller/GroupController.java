package com.spbstu.curse.controller;

import com.spbstu.curse.dto.GroupDto;
import com.spbstu.curse.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
@RequiredArgsConstructor
public class GroupController {
  private final GroupService service;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<GroupDto>> getAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<GroupDto> getGroup(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @DeleteMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public void deleteGroup(@RequestBody GroupDto groupDto) {
    service.delete(groupDto);
  }

  @PutMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public ResponseEntity<GroupDto> putGroup(@RequestBody GroupDto groupDto) {
    return ResponseEntity.ok(service.put(groupDto));
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public ResponseEntity<GroupDto> addGroup(@RequestBody GroupDto groupDto) {
    return ResponseEntity.ok(service.save(groupDto));
  }
}
