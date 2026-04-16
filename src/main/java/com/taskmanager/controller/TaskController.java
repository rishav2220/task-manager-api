package com.taskmanager.controller;

  import com.taskmanager.model.Task;
  import com.taskmanager.service.TaskService;
  import jakarta.validation.Valid;
  import lombok.RequiredArgsConstructor;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.security.core.annotation.AuthenticationPrincipal;
  import org.springframework.security.core.userdetails.UserDetails;
  import org.springframework.web.bind.annotation.*;

  import java.util.List;

  @RestController
  @RequestMapping("/api/tasks")
  @RequiredArgsConstructor
  @CrossOrigin(origins = "http://localhost:3000")
  public class TaskController {

      private final TaskService taskService;

      @GetMapping
      public ResponseEntity<List<Task>> getAllTasks(
              @AuthenticationPrincipal UserDetails userDetails,
              @RequestParam(required = false) Task.Status status) {
          List<Task> tasks = taskService.getTasksForUser(userDetails.getUsername(), status);
          return ResponseEntity.ok(tasks);
      }

      @GetMapping("/{id}")
      public ResponseEntity<Task> getTask(
              @PathVariable Long id,
              @AuthenticationPrincipal UserDetails userDetails) {
          Task task = taskService.getTaskById(id, userDetails.getUsername());
          return ResponseEntity.ok(task);
      }

      @PostMapping
      public ResponseEntity<Task> createTask(
              @Valid @RequestBody Task task,
              @AuthenticationPrincipal UserDetails userDetails) {
          Task created = taskService.createTask(task, userDetails.getUsername());
          return ResponseEntity.status(HttpStatus.CREATED).body(created);
      }

      @PutMapping("/{id}")
      public ResponseEntity<Task> updateTask(
              @PathVariable Long id,
              @Valid @RequestBody Task task,
              @AuthenticationPrincipal UserDetails userDetails) {
          Task updated = taskService.updateTask(id, task, userDetails.getUsername());
          return ResponseEntity.ok(updated);
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Void> deleteTask(
              @PathVariable Long id,
              @AuthenticationPrincipal UserDetails userDetails) {
          taskService.deleteTask(id, userDetails.getUsername());
          return ResponseEntity.noContent().build();
      }
  }
  