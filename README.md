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
