# Library API

A REST API for managing books, built with Spring Boot and PostgreSQL.

## Tech Stack
- Java 21
- Spring Boot (Spring Web, Spring Data JPA)
- PostgreSQL
- Lombok
- Maven

## Architecture
The project follows a layered architecture:

    Controller  →  handles HTTP requests and responses
    Service     →  contains business logic
    Repository  →  communicates with the database

## API Endpoints
| Method | Endpoint      | Description        | Success | Error |
|--------|---------------|--------------------|---------|-------|
| GET    | /books        | Get all books      | 200     | –     |
| GET    | /books/{id}   | Get a book by ID   | 200     | 404   |
| POST   | /books        | Create a new book  | 201     | –     |
| PUT    | /books/{id}   | Update a book      | 200     | 404   |
| DELETE | /books/{id}   | Delete a book      | 204     | 404   |

### Example request
`POST /books`
```json
{
  "title": "O'tkan kunlar",
  "author": "Abdulla Qodiriy"
}
```

## Getting Started

### Prerequisites
- Java 21
- PostgreSQL

### Setup
1. Create the database:
   ```sql
   CREATE DATABASE library;
   ```
2. Set the database password as an environment variable:
   ```bash
   export DB_PASSWORD=your_password
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`.