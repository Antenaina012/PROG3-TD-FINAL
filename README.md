# Agricultural Federation Management API - PROG3-TD-FINAL

## Project Overview
Spring Boot REST API for managing agricultural collectivities, members, and federation operations in Madagascar.

## Completed Implementation

### Section A: Collectivity Creation
- **Endpoint**: `POST /collectivities` - Create new collectivities
- **Validation**:
  - Federation approval required
  - Minimum 10 founding members
  - At least 5 members with 6+ months seniority
- **GET /collectivities** - Retrieve all collectivities

### Section B-2: Member Enrollment  
- **Endpoint**: `POST /members` - Register new members
- **Validation**:
  - Minimum 2 confirmed sponsors (referees)
  - More internal than external sponsors required
  - Registration fee + annual dues must be paid
  - Sponsors must have 6+ months tenure
- **GET /members** - Retrieve all members

## Project Structure
```
src/
├── main/java/org/example/examprog3/
│   ├── controller/          # REST endpoints
│   ├── Service/             # Business logic
│   ├── repository/          # Data access
│   ├── entity/              # Domain models & DTOs
│   ├── validator/           # Business validation rules
│   ├── exeption/            # Custom exceptions
│   └── config/              # Spring configuration
├── resources/
│   └── application.properties
└── test/
    └── ExamProg3ApplicationTests.java

docs/
└── openapi.yaml            # Full API specification

```

## Technology Stack
- **Framework**: Spring Boot 4.0.5
- **Language**: Java 21
- **Build Tool**: Maven 3.9.14
- **Database**: PostgreSQL
- **Dependencies**:
  - Lombok (annotation processing)
  - Spring Web MVC
  - Spring Test

## How to Build & Run
```bash
# Build the project
./mvnw clean package

# Run the application
java -jar target/ExamProg3-0.0.1-SNAPSHOT.jar
```

## Database Configuration
Set environment variables or modify `DataSource.java`:
- `JDBC_URL`: jdbc:postgresql://localhost:5432/federation_db
- `DB_USER`: postgres
- `DB_PASSWORD`: (configure as needed)

## API Endpoints

### Collectivities
- `GET /collectivities` - List all collectivities
- `POST /collectivities` - Create new collectivity

### Members
- `GET /members` - List all members
- `POST /members` - Enroll new member(s)

## Validation Rules

### CollectivityValidator
- Federation approval is mandatory
- Minimum 10 members required
- At least 5 members must have 6+ months seniority

### MemberValidator & SponsorCountValidator
- Minimum 2 sponsors required
- At least as many internal sponsors as external sponsors

### SponsorTenureValidator
- All sponsors must have 6+ months of membership

### PaymentValidator
- Registration fee must be paid
- Annual membership dues must be paid

## Error Handling
Custom exceptions with HTTP status codes:
- `InsufficientSponsorCount` (400 Bad Request)
- `SponsorTenureException` (400 Bad Request)
- `PaymentException` (400 Bad Request)
- `NotFoundException` (404 Not Found)

## Authors
STD24115 & STD24174
