package com.taskmanager.controller;

  import com.taskmanager.model.User;
  import com.taskmanager.security.JwtUtil;
  import com.taskmanager.service.UserService;
  import jakarta.validation.Valid;
  import lombok.RequiredArgsConstructor;
  import org.springframework.http.ResponseEntity;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
  import org.springframework.security.core.Authentication;
  import org.springframework.web.bind.annotation.*;

  import java.util.Map;

  @RestController
  @RequestMapping("/api/auth")
  @RequiredArgsConstructor
  @CrossOrigin(origins = "http://localhost:3000")
  public class AuthController {

      private final AuthenticationManager authenticationManager;
      private final JwtUtil jwtUtil;
      private final UserService userService;

      @PostMapping("/register")
      public ResponseEntity<Map<String, String>> register(@Valid @RequestBody User user) {
          userService.registerUser(user);
          return ResponseEntity.ok(Map.of("message", "User registered successfully"));
      }

      @PostMapping("/login")
      public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
          Authentication auth = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  credentials.get("username"),
                  credentials.get("password")
              )
          );
          String token = jwtUtil.generateToken(auth.getName());
          return ResponseEntity.ok(Map.of("token", token, "username", auth.getName()));
      }
  }
  