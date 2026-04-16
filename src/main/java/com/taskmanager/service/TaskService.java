package com.taskmanager.service;

  import com.taskmanager.model.Task;
  import com.taskmanager.model.User;
  import com.taskmanager.repository.TaskRepository;
  import lombok.RequiredArgsConstructor;
  import org.springframework.http.HttpStatus;
  import org.springframework.stereotype.Service;
  import org.springframework.web.server.ResponseStatusException;

  import java.util.List;

  @Service
  @RequiredArgsConstructor
  public class TaskService {

      private final TaskRepository taskRepository;
      private final UserService userService;

      public List<Task> getTasksForUser(String username, Task.Status status) {
          User user = userService.findByUsername(username);
          if (status != null) {
              return taskRepository.findByUserAndStatus(user, status);
          }
          return taskRepository.findByUser(user);
      }

      public Task getTaskById(Long id, String username) {
          Task task = taskRepository.findById(id)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
          if (!task.getUser().getUsername().equals(username)) {
              throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
          }
          return task;
      }

      public Task createTask(Task task, String username) {
          User user = userService.findByUsername(username);
          task.setUser(user);
          return taskRepository.save(task);
      }

      public Task updateTask(Long id, Task updated, String username) {
          Task task = getTaskById(id, username);
          task.setTitle(updated.getTitle());
          task.setDescription(updated.getDescription());
          task.setStatus(updated.getStatus());
          task.setPriority(updated.getPriority());
          task.setDueDate(updated.getDueDate());
          return taskRepository.save(task);
      }

      public void deleteTask(Long id, String username) {
          Task task = getTaskById(id, username);
          taskRepository.delete(task);
      }
  }
  