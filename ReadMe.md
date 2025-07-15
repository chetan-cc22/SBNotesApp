# Notes App 📝

This is a Spring Boot Notes Application with:
- JWT Authentication
- MySQL Database
- CRUD for Notes
- Secure REST APIs

## Run Locally

### 1. Start MySQL and create DB:
CREATE DATABASE noteapp;

shell
Copy
Edit

### 2. Configure `application.properties`
spring.datasource.url=jdbc:mysql://localhost:3306/noteapp
spring.datasource.username=root
spring.datasource.password=

shell
Copy
Edit

### 3. Run Project

Use IntelliJ or:
./mvnw spring-boot:run

yaml
Copy
Edit

### 4. Test API in Postman
- Login endpoint: `/login`
- Notes CRUD: `/notes` (protected by JWT)

---

## Author
Chetan Chaudhari