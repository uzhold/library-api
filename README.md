# Library API

A REST API for managing books and authors, built with Spring Boot and PostgreSQL and secured with JWT authentication.

## Tech Stack
- Java 21
- Spring Boot (Spring Web, Spring Data JPA, Spring Security, Validation)
- PostgreSQL
- JWT (jjwt 0.13)
- Lombok
- Maven
- JUnit 5, Mockito

## Features
- CRUD for books and authors (one author has many books)
- Request validation with clear `400` error messages
- Global exception handling (`404`, `409`, `401`)
- User registration with BCrypt-hashed passwords
- Stateless JWT authentication (`Authorization: Bearer <token>`)
- Role-based access: `USER` and `ADMIN`
- Unit tests for services and JWT logic

## Architecture
The project follows a layered architecture:

    Controller  →  handles HTTP requests and responses
    Service     →  contains business logic
    Repository  →  communicates with the database

Security flow:

    POST /auth/login  →  check password (BCrypt)  →  return signed JWT
    Next requests     →  JwtAuthFilter checks the token  →  user is authenticated

## API Endpoints

### Auth
| Method | Endpoint       | Description                 | Access | Success | Error    |
|--------|----------------|-----------------------------|--------|---------|----------|
| POST   | /auth/register | Register a new user         | Public | 201     | 400, 409 |
| POST   | /auth/login    | Log in and receive a JWT    | Public | 200     | 400, 401 |

### Books
| Method | Endpoint      | Description       | Access        | Success | Error         |
|--------|---------------|-------------------|---------------|---------|---------------|
| GET    | /books        | Get all books     | Public        | 200     | –             |
| GET    | /books/{id}   | Get a book by ID  | Public        | 200     | 404           |
| POST   | /books        | Create a book     | Authenticated | 201     | 400, 401, 404 |
| PUT    | /books/{id}   | Update a book     | Authenticated | 200     | 401, 404      |
| DELETE | /books/{id}   | Delete a book     | ADMIN         | 204     | 401, 403, 404 |

### Authors
| Method | Endpoint             | Description              | Access        | Success | Error         |
|--------|----------------------|--------------------------|---------------|---------|---------------|
| GET    | /authors             | Get all authors          | Public        | 200     | –             |
| GET    | /authors/{id}        | Get an author by ID      | Public        | 200     | 404           |
| GET    | /authors/{id}/books  | Get books by an author   | Public        | 200     | –             |
| POST   | /authors             | Create an author         | Authenticated | 201     | 400, 401      |
| PUT    | /authors/{id}        | Update an author         | Authenticated | 200     | 400, 401, 404 |
| DELETE | /authors/{id}        | Delete an author         | ADMIN         | 204     | 401, 403, 404 |

## Authentication

1. Register:
   ```http
   POST /auth/register
   Content-Type: application/json

   { "username": "shoh", "password": "12345678" }
   ```
2. Log in and copy the token from the response:
   ```http
   POST /auth/login
   Content-Type: application/json

   { "username": "shoh", "password": "12345678" }
   ```
   ```json
   { "token": "eyJhbGciOiJIUzI1NiJ9..." }
   ```
3. Send the token with protected requests:
   ```http
   POST /books
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
   Content-Type: application/json

   { "title": "O'tkan kunlar", "authorId": 1 }
   ```

Tokens are valid for 1 hour. New users get the `USER` role; `ADMIN` is assigned in the database.

## Getting Started

### Prerequisites
- Java 21
- PostgreSQL

### Setup
1. Create the database:
   ```sql
   CREATE DATABASE library;
   ```
2. Set environment variables (never commit real values):
   ```bash
   export DB_PASSWORD=your_db_password
   export JWT_SECRET=$(openssl rand -base64 32)
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`.

### Run tests
```bash
./mvnw test
```
