# Enterprise Digital Loan Management System

## Overview
The Enterprise Digital Loan Management System is a full-stack banking application
designed to manage the complete loan lifecycle in a financial institution.
It supports loan applications, approvals, EMI calculation, and repayment tracking
using a modern enterprise architecture.

## Key Features
- Role-based loan management (Customer, Loan Officer, Admin)
- Loan application and approval workflow
- Automatic EMI calculation and schedule generation
- RESTful APIs for frontend-backend communication
- Global exception handling with proper HTTP responses
- API documentation using Swagger
- Fully containerized deployment using Docker and Docker Compose

## Technology Stack
### Backend
- Java 25
- Spring Boot (Spring MVC)
- Spring Data JPA
- MySQL
- Swagger / OpenAPI

### Frontend
- React
- Axios

### DevOps
- Docker
- Docker Compose

## System Architecture
- React frontend communicates with Spring MVC backend via REST APIs
- Backend follows layered architecture (Controller, Service, Repository)
- MySQL used for relational and transactional data
- Docker Compose orchestrates frontend, backend, and database containers

## Core Modules
- User Management
- Loan Application & Processing
- EMI Calculation & Scheduling
- Loan Status Tracking
- API Documentation

## How to Run the Project (Docker)
1. Clone the repository
2. Navigate to project root
3. Run: docker-compose up --build
4. Access:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html

## Future Enhancements
- JWT-based authentication
- Role-based authorization
- Payment gateway integration
- CI/CD pipeline integration
