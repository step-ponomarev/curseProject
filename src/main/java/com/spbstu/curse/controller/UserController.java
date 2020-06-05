package com.spbstu.curse.controller;

import com.spbstu.curse.authorities.User;
import com.spbstu.curse.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public User currentUser(Principal principal) {
    if (principal == null) {
      return null;
    }

    return userRepository.findByUsername(principal.getName());
  }

  @GetMapping("all")
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public ResponseEntity<List<User>> getAll() {
    return ResponseEntity.ok(StreamSupport
        .stream(userRepository.findAll().spliterator(), false)
        .collect(Collectors.toList()));
  }

  @PostMapping
  public ResponseEntity<User> register(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return ResponseEntity.ok(userRepository.save(user));
  }
}
