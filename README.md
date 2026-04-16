# Task Manager API

  A full-stack task management application built with **Spring Boot** (backend) and **React** (frontend). Users can register, log in with JWT, and manage tasks through a clean REST API.

  ## Tech Stack

  **Backend**
  - Java 17 + Spring Boot 3.2
  - Spring Security + JWT Authentication
  - MySQL 8 + Spring Data JPA
  - Maven

  **Frontend**
  - React 18
  - Axios (HTTP client)
  - React Router v6

  ## Features

  - User registration and login with JWT tokens
  - Create, read, update, and delete tasks
  - Filter tasks by status (TODO, IN_PROGRESS, DONE)
  - Each user sees only their own tasks
  - Input validation and error handling

  ## Getting Started

  ### Prerequisites
  - Java 17+
  - Maven 3.8+
  - MySQL 8+
  - Node.js 18+

  ### Backend Setup

  1. Clone the repo
  ```bash
  git clone https://github.com/rishav2220/task-manager-api.git
  cd task-manager-api
  ```

  2. Create a MySQL database
  ```sql
  CREATE DATABASE taskmanager;
  ```

  3. Update `src/main/resources/application.properties` with your DB credentials.

  4. Run the application
  ```bash
  mvn spring-boot:run
  ```

  The API will start at `http://localhost:8080`.

  ### Frontend Setup

  ```bash
  cd frontend
  npm install
  npm start
  ```

  The React app will start at `http://localhost:3000`.

  ## API Endpoints

  | Method | Endpoint | Description | Auth |
  |--------|----------|-------------|------|
  | POST | /api/auth/register | Register a new user | No |
  | POST | /api/auth/login | Login and get JWT token | No |
  | GET | /api/tasks | Get all tasks for user | Yes |
  | POST | /api/tasks | Create a task | Yes |
  | PUT | /api/tasks/{id} | Update a task | Yes |
  | DELETE | /api/tasks/{id} | Delete a task | Yes |

  ## Project Structure

  ```
  src/
  └── main/
      ├── java/com/taskmanager/
      │   ├── controller/    # REST controllers
      │   ├── model/         # JPA entities
      │   ├── repository/    # Spring Data repositories
      │   ├── service/       # Business logic
      │   └── security/      # JWT config and filters
      └── resources/
          ├── application.properties
          └── schema.sql
  frontend/
  └── src/
      ├── components/        # React components
      ├── services/          # API calls
      └── App.jsx
  ```
  