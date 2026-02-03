# 🏥 Healthcare Appointment & Patient Management System (HAPMS)

A Spring Boot backend application for managing patients, doctors, appointments, and prescriptions in a healthcare system.

## 🚀 Features
- JWT Authentication & Role-based Authorization
- Roles: ADMIN, DOCTOR, PATIENT
- Appointment booking with business rules
- Prescription management
- Stream API based reports
- Global exception handling
- MySQL + JPA (Hibernate)
- Docker-ready

## 🧱 Tech Stack
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Maven

## 🔐 Roles & Access
| Role | Permissions |
|----|----|
| ADMIN | Manage patients, appointments |
| DOCTOR | View appointments, create prescriptions |
| PATIENT | Book appointments |

## ▶️ Run Locally
```bash
mvn clean install
mvn spring-boot:run

## Key Features
- JWT auth + Refresh tokens
- Logout with Redis token blacklist
- Role-based authorization (ADMIN/DOCTOR/PATIENT)
- Concurrency-safe slot booking (pessimistic locking)
- Appointment conflict detection (time overlap algorithm)
- Advanced search (dynamic filtering via JPA Specifications)
- Clinical records: allergies + medical history
- Lab report upload/download
- Admin dashboard stats and CSV exports
- Redis cache + scheduled reminder jobs

## Tech Stack
Java • Spring Boot • Spring Security • JPA/Hibernate • MySQL • Redis • Maven

## Running Locally
1. Start MySQL and Redis
2. Configure `application.properties`
3. Run:
   ```bash
   mvn spring-boot:run
