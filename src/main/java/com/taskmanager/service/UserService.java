package com.taskmanager.service;

  import com.taskmanager.model.User;
  import com.taskmanager.repository.UserRepository;
  import lombok.RequiredArgsConstructor;
  import org.springframework.http.HttpStatus;
  import org.springframework.security.crypto.password.PasswordEncoder;
  import org.springframework.stereotype.Service;
  import org.springframework.web.server.ResponseStatusException;

  @Service
  @RequiredArgsConstructor
  public class UserService {

      private final UserRepository userRepository;
      private final PasswordEncoder passwordEncoder;

      public User registerUser(User user) {
          if (userRepository.existsByUsername(user.getUsername())) {
              throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
          }
          if (userRepository.existsByEmail(user.getEmail())) {
              throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
          }
          user.setPassword(passwordEncoder.encode(user.getPassword()));
          return userRepository.save(user);
      }

      public User findByUsername(String username) {
          return userRepository.findByUsername(username)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
      }
  }
  