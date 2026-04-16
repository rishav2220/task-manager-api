package com.taskmanager.model;

  import jakarta.persistence.*;
  import jakarta.validation.constraints.Email;
  import jakarta.validation.constraints.NotBlank;
  import jakarta.validation.constraints.Size;
  import lombok.Data;
  import java.time.LocalDateTime;
  import java.util.ArrayList;
  import java.util.List;

  @Data
  @Entity
  @Table(name = "users")
  public class User {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @NotBlank
      @Size(min = 3, max = 50)
      @Column(nullable = false, unique = true)
      private String username;

      @NotBlank
      @Email
      @Column(nullable = false, unique = true)
      private String email;

      @NotBlank
      @Column(nullable = false)
      private String password;

      @Column(updatable = false)
      private LocalDateTime createdAt;

      @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<Task> tasks = new ArrayList<>();

      @PrePersist
      protected void onCreate() {
          createdAt = LocalDateTime.now();
      }
  }
  