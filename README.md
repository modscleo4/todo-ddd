# todo-ddd

A modern Todo application backend built with **Spring Boot 4** and **Java 25**. This project serves as a showcase for high-performance, secure, and observable Java microservices.

## Technologies

- **Java 25**: Leveraging the latest language features like Scoped Values, Pattern Matching, and Unnamed Variables.
- **Spring Boot 4**: The next generation of the Spring ecosystem.
- **Spring Security**: Robust authentication and authorization with JWT (JSON Web Tokens).
- **Spring Data JPA**: Persistence layer with PostgreSQL and SQLite support.
- **Flyway**: Database migrations for schema versioning.
- **Project Lombok**: Reducing boilerplate for domain entities.
- **Springdoc OpenAPI**: Automatic Swagger UI generation for API documentation.
- **Virtual Threads**: Optimized for high-concurrency and throughput.

## Project Structure

- `br.dev.modscleo4.todo.application`: Service layer and repositories.
- `br.dev.modscleo4.todo.domain`: Core domain entities and exceptions.
- `br.dev.modscleo4.todo.infrastructure`: Configuration (Security, Web, JWT) and Controllers.

## Authentication

The project uses OAuth2-compatible JWT authentication.
- **Access Tokens**: Short-lived, used for API requests.
- **Refresh Tokens**: Long-lived, used to obtain new access tokens.
- **ECDSA Signing**: Tokens are signed using Elliptic Curve Digital Signature Algorithm (ES256).

## API Endpoints

### Auth
- `POST /auth/sign-up`: Register a new user.
- `GET /auth/user`: Get current authenticated user info.
- `POST /oauth/token`: Generate access/refresh tokens.

### Notes
- `GET /v1/notes/`: List all notes (paginated).
- `POST /v1/notes/`: Create a new note.
- `GET /v1/notes/{id}`: Get note details.
- `PATCH /v1/notes/{id}`: Partially update a note (title, content, done).
- `DELETE /v1/notes/{id}`: Delete a note.

### Profiles
- `GET /v1/profiles/`: Get user profile.
- `POST /v1/profiles/`: Create user profile.
- `PATCH /v1/profiles/`: Update user profile.

## Configuration

The application requires several environment variables for security and database connectivity:

- `DB_URL`: JDBC connection string.
- `JWT_ISSUER`: Token issuer name.
- `JWT_PUBLIC_KEY`: Base64 encoded EC public key.
- `JWT_PRIVATE_KEY`: Base64 encoded EC private key.

## Getting Started

### Prerequisites
- JDK 25
- Maven 3.9+

### Running the application
```bash
./mvnw spring-boot:run
```

### Running tests
```bash
./mvnw test
```

## 📖 API Documentation
Once running, you can access the Swagger UI at:
`http://localhost:8080/swagger-ui/index.html`
