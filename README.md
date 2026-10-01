# 📝 EasyNotes — REST API Backend

A secure, production-structured REST API backend for a note-taking application built with **Java** and **Spring Boot**. Features JWT-based stateless authentication, per-user note management, global exception handling, and standardized API responses.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Security | Spring Security, JWT (jjwt 0.11.5) |
| ORM | Spring Data JPA, Hibernate |
| Build Tool | Maven |
| API Docs | Swagger / OpenAPI |

---

## ✨ Features

- 🔐 **JWT Authentication** — Stateless auth with 1-hour token expiry using HMAC-SHA signing
- 👤 **User Management** — Signup with BCrypt password encoding, login with token response
- 📓 **Notes CRUD** — Create, read, update, delete notes scoped per user
- 🛡️ **Security Filter** — Custom `OncePerRequestFilter` middleware for token validation on every request
- 📦 **Standardized Responses** — Generic `ResponseWrapper<T>` applied globally via `ResponseBodyAdvice`
- ⚠️ **Global Exception Handling** — `@RestControllerAdvice` covering `NoSuchElementException`, `IllegalArgumentException`, and fallback exceptions
- 🌱 **App Bootstrapper** — `CommandLineRunner` seeds a default user on startup for instant testing

---

## 🏗️ Project Structure

```
src/
├── controller/
│   ├── auth/           # AuthController  — signup, login
│   └── notes/          # NotesController — CRUD endpoints
├── service/
│   ├── AuthService     # Auth logic, BCrypt, JWT generation
│   ├── NotesService    # Note upsert, delete, fetch by user
│   └── UserService     # UserDetailsService implementation
├── entity/
│   ├── UserEntity      # Implements UserDetails
│   └── NotesEntity     # Note table mapping
├── repository/
│   ├── UserRepository  # JPA + custom findByUsername
│   └── NotesRepository # JPA + custom findByUserId
├── dependency/
│   ├── SecurityConfiguration  # SecurityFilterChain, BCrypt bean
│   └── AuthMiddleware         # OncePerRequestFilter — JWT validation
├── interceptors/
│   ├── ExceptionInterceptor   # @RestControllerAdvice — error handling
│   └── ResponseInterceptor    # ResponseBodyAdvice — response wrapping
├── DTO/
│   ├── request/        # LoginRequestDto, SignupRequestDto, NotesDto
│   └── response/       # AuthResponse
└── util/
    ├── AuthUtil        # JWT generation & claims extraction
    ├── ResponseWrapper # Generic API response envelope
    └── AppBootstrapper # CommandLineRunner — seed data
```

---

## 🔐 Authentication Flow

```
Client                          Server
  │                               │
  │──── POST /auth/signup ────────▶│  BCrypt encode password → save user
  │                               │
  │──── POST /auth/login ─────────▶│  Authenticate → generate JWT
  │◀─── { id, name, accessToken }──│
  │                               │
  │──── GET /notes/{userId} ──────▶│  AuthMiddleware extracts token
  │   Authorization: Bearer <jwt> │  → validate → set SecurityContext
  │◀─── { success, data: [...] } ──│
```

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/auth/signup` | Register new user | ❌ |
| `POST` | `/auth/login` | Login and get JWT token | ❌ |

### Notes
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/notes/{userId}` | Get all notes for a user | ✅ |
| `POST` | `/notes/create` | Create a new note | ✅ |
| `POST` | `/notes/update` | Update an existing note | ✅ |
| `DELETE` | `/notes/{noteId}` | Delete a note | ✅ |

---

## 📦 Request & Response Examples

### Signup
```json
POST /auth/signup
{
  "name": "Alex Macias",
  "username": "alexm03",
  "password": "Abcd@1234"
}
```

### Login
```json
POST /auth/login
{
  "username": "alexm03",
  "password": "Abcd@1234"
}

// Response
{
  "success": true,
  "message": "Success",
  "data": {
    "id": 1,
    "name": "Alex Macias",
    "username": "alexm03",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
```

### Create Note
```json
POST /notes/create
Authorization: Bearer <token>

{
  "userId": 1,
  "title": "My First Note",
  "description": "This is the note content"
}

// Response
{
  "success": true,
  "message": "Success",
  "data": [
    { "id": 1, "title": "My First Note", "description": "This is the note content" }
  ]
}
```

### Error Response
```json
{
  "success": false,
  "message": "User doesn't exist !",
  "data": null
}
```

---

## 🔒 Security Design

- **Stateless sessions** — `SessionCreationPolicy.STATELESS`, no server-side session stored
- **Password encoding** — BCrypt via `BCryptPasswordEncoder` bean
- **Token validation** — `AuthMiddleware` extends `OncePerRequestFilter`, extracts and validates JWT on every request before it reaches the controller
- **`UserEntity` implements `UserDetails`** — tightly integrates the user domain model with Spring Security
- **Public routes** — `/auth/**` is open; all other routes require a valid Bearer token
- **CSRF disabled** — appropriate for stateless JWT-based APIs

---

## 📐 Response Envelope

Every API response is automatically wrapped in a consistent envelope:

```json
{
  "success": true | false,
  "message": "Success" | "Error message",
  "data": <response payload>
}
```

This is implemented via `ResponseInterceptor` which implements `ResponseBodyAdvice<Object>` — it intercepts every controller response and wraps it before serialization, without touching individual controller methods.

---

## ⚠️ Exception Handling

All exceptions are caught globally by `ExceptionInterceptor` (`@RestControllerAdvice`):

| Exception | HTTP Status |
|---|---|
| `NoSuchElementException` | `404 Not Found` |
| `IllegalArgumentException` | `400 Bad Request` |
| `Exception` (fallback) | `500 Internal Server Error` |

---

## ⚙️ Configuration

```properties
# application.properties
spring.application.name=easynotes
jwt.secretKey=<your-secret-key>
logging.level.org.springframework=debug
spring.jpa.show-sql=true
```

---

## 🏃 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+

### Run Locally

```bash
# Clone the repository
git clone https://github.com/your-username/easynotesbackend.git
cd easynotesbackend

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`

> A default user (`alexm03` / `Abcd@1234`) is seeded automatically on startup via `AppBootstrapper`.

---

## 🧪 Testing the API

Use **Postman** or any REST client:

1. Hit `POST /auth/login` with the seeded credentials
2. Copy the `accessToken` from the response
3. Add `Authorization: Bearer <token>` header to all `/notes/**` requests

---

## 📌 Key Design Decisions

| Decision | Reason |
|---|---|
| `UserEntity` implements `UserDetails` | Avoids a separate adapter class; keeps the domain model self-contained |
| `ResponseBodyAdvice` for wrapping | Centralizes response structure without polluting individual controllers |
| `OncePerRequestFilter` for JWT | Guarantees exactly one execution per request regardless of filter chain order |
| `CommandLineRunner` for seed data | Allows instant testing without manual setup |

---

## 👨‍💻 Author

**Subham Biswas**
[LinkedIn](https://linkedin.com/in/subham-biswas-b118661bb) · [GitHub](https://github.com/your-username)
