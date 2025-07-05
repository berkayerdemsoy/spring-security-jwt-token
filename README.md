# 🛡️ Spring Boot 3 JWT Authentication API

This is a secure RESTful API built with **Spring Boot 3**, implementing **JWT (JSON Web Token)** based authentication and authorization. It includes user registration, login, role-based access control, and stateless session management.

> 💡 This project uses a **locally installed MySQL database**, not Docker.

---

## ✅ Features

- User registration (`/register`)
- User login (`/login`)
- JWT token generation and validation
- Stateless authentication using JWT
- Role-based access control (`USER`, `ADMIN`)
- Secure endpoints with Spring Security
- DTO-layer, clean architecture, and service-based design

---

## 🧰 Technologies

- Java 21
- Spring Boot 3.x
- Spring Security
- JJWT (v0.12.6)
- MySQL (local)
- Lombok
- Maven

---

## 🔐 JWT Authentication Flow

1. User registers using the `/register` endpoint.
2. User logs in via `/login` with valid credentials.
3. Server generates a **JWT** and returns it in the response.
4. Client includes the token in all protected requests as a Bearer token:


5. A custom filter (`JwtFilter`) extracts the token, validates it, and authenticates the user by populating the `SecurityContext`.

---

## 🔐 Security Configuration

- JWT is signed using HMAC-SHA256 (`HS256`)
- Tokens include expiration claims
- No server-side session is stored (`STATELESS`)
- Spring Security `SecurityContext` is dynamically populated on every request
- Role-based access is enforced with `hasRole` and `hasAnyRole`

---

## 🔧 Sample Endpoints

| Endpoint     | Method | Access Rule                        |
|--------------|--------|-------------------------------------|
| `/`          | GET    | Public                             |
| `/register`  | POST   | Public                             |
| `/login`     | POST   | Public                             |
| `/hello`     | GET    | Authenticated users (any role)     |
| `/user`      | GET    | `ROLE_USER` or `ROLE_ADMIN`        |
| `/admin`     | GET    | `ROLE_ADMIN` only                  |

---

## ⚙️ MySQL Configuration (application.properties)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/berkai-db
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
