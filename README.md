# Betting Management System

This project is a Betting Management System built with Spring Boot. It provides endpoints for managing sports matches and associated betting odds. The system is designed to support CRUD operations for matches and odds.

## Features

- **Match Management**: Create, update, retrieve, and delete sports matches.
- **Odds Management**: Create, update, retrieve, and delete betting odds for different match outcomes (e.g., Home Win, Draw, Away Win).
- **Data Validation**: Ensures that match and odds data are valid.

  

## Technology Stack

- **Java 21**
- **Spring Boot**
- **PostgreSQL** for data storage
- **Flyway** for database migrations
- **Docker** for containerization


## Prerequisites

- **Docker** and **Docker Compose** installed on your machine.

## Getting Started

### Clone the repository

```
git clone https://github.com/IliasPapargiris/betting-management-system.git
```
### Building and Running the Application with Docker
   The application is containerized using Docker. To build and run the application, follow these steps:

    
    docker-compose up --build
    
    
## Access the Application

- **Betting Management API**: [http://localhost:8080/api/matches)
- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html#)

### Using the API
Once the application is running, you can use Swagger to find the API endpoints with provided request and response examples. 

You're ready to go! 
