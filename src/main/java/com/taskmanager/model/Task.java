package com.taskmanager.model;

  import jakarta.persistence.*;
  import jakarta.validation.constraints.NotBlank;
  import jakarta.validation.constraints.Size;
  import lombok.Data;
  import java.time.LocalDate;
  import java.time.LocalDateTime;

  @Data
  @Entity
  @Table(name = "tasks")
  public class Task {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @NotBlank(message = "Title is required")
      @Size(max = 200, message = "Title must not exceed 200 characters")
      @Column(nullable = false)
      private String title;

      @Column(columnDefinition = "TEXT")
      private String description;

      @Enumerated(EnumType.STRING)
      @Column(nullable = false)
      private Status status = Status.TODO;

      @Enumerated(EnumType.STRING)
      @Column(nullable = false)
      private Priority priority = Priority.MEDIUM;

      private LocalDate dueDate;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id", nullable = false)
      private User user;

      @Column(updatable = false)
      private LocalDateTime createdAt;

      private LocalDateTime updatedAt;

      @PrePersist
      protected void onCreate() {
          createdAt = LocalDateTime.now();
          updatedAt = LocalDateTime.now();
      }

      @PreUpdate
      protected void onUpdate() {
          updatedAt = LocalDateTime.now();
      }

      public enum Status { TODO, IN_PROGRESS, DONE }
      public enum Priority { LOW, MEDIUM, HIGH }
  }
  