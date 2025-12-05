# PadelPal | Distributed Padel Court Booking Platform

A microservices-based backend system demonstrating distributed service design, inter-service communication, and containerized deployment using Docker.

---

## Team Members
- Petrec Matei-Teodor  
- Bontaș Andrian-Cosmin

---

# System Architecture

PadelPal is composed of **three independent microservices**, each following the Single Responsibility Principle and maintaining its own internal H2 database.

### **1. User Service**
- **Port:** 8081  
- **Role:** Manages user data  
- **Endpoints:** Create user, retrieve user, list users  
- **Used by:** Booking Service for user validation  

### **2. Court Service**
- **Port:** 8082  
- **Role:** Manages padel courts  
- **Endpoints:** Create court, retrieve court, list courts  
- **Used by:** Booking Service for court validation  

### **3. Booking Service**
- **Port:** 8083  
- **Role:** Orchestrates bookings  
- **Endpoints:** Create booking, list bookings, get bookings per user  
- **Inter-service communication:** Calls User and Court services via REST  

---

# Inter-Service Data Flow

### **Booking Workflow**
1. Client sends:  
   `POST /api/bookings?userId=X&courtId=Y`
2. Booking Service calls User Service → `GET /api/users/{id}`
3. Booking Service calls Court Service → `GET /api/courts/{id}`
4. If both exist → booking is created  
5. Otherwise → **400 Bad Request**  

---

# Deployment Instructions

## **Prerequisites**
- Docker Desktop  
- Postman  
- Maven pre-built JARs  

---

# **Phase 1: System Startup**

We use **Docker Compose** to orchestrate the build and network bridging for all three microservices.

1. Clone the repository  
2. Open a terminal inside the project root 
3. Build & start all services:

```
docker-compose up --build
```

4. Wait until logs show:

```
Started UserServiceApplication
Started CourtServiceApplication
Started BookingServiceApplication
```

Insert Docker Desktop screenshot here:  
`docker-running.png`

---

# **Phase 2: Verification**

Check that all containers are running:

```
docker ps
```

Expected:

- padelpal-user-service  
- padelpal-court-service  
- padelpal-booking-service  

---

# Quality Assurance (Testing)

A complete Postman collection is included at:

```
postman/PadelPal_Postman_Collection.json
```

---

## **Import Procedure**

1. Open Postman  
2. Click Import  
3. Drag & drop the JSON file  
4. Confirm the collection appears in the sidebar  

---

# Execution Sequence (Real User Journey Simulation)

To ensure correct system behavior, the tests must be executed in this order.

| # | **Service** | **Action** | **Endpoint** | **Explanation** |
|---|---------|--------|----------|-------------|
| **1** | User Service | Create User | `POST /api/users` | Registers a new user. |
| **2** | Court Service | Create Court | `POST /api/courts` | Adds a court into the system. |
| **3** | User Service | Get User | `GET /api/users/{id}` | Confirms the user exists. |
| **4** | Court Service | Get Court | `GET /api/courts/{id}` | Confirms the court exists. |
| **5** | Booking Service | Create Booking | `POST /api/bookings?userId=X&courtId=Y` | Creates booking after validation. |
| **6** | Booking Service | List Bookings | `GET /api/bookings` | Ensures booking is registered. |
| **7** | Booking Service | Get Bookings by User | `GET /api/bookings/user/{userId}` | Shows user-specific bookings. |

---

# Additional API Endpoints

### **User Service**
| Purpose | Endpoint |
|--------|----------|
| Get user by ID | `GET /api/users/{id}` |
| List all users | `GET /api/users` |

### **Court Service**
| Purpose | Endpoint |
|--------|----------|
| Get court by ID | `GET /api/courts/{id}` |
| List all courts | `GET /api/courts` |

### **Booking Service**
| Purpose | Endpoint |
|--------|----------|
| Create booking | `POST /api/bookings?userId=X&courtId=Y` |
| List all bookings | `GET /api/bookings` |
| List bookings by user | `GET /api/bookings/user/{userId}` |

---

# Operational Management

### Stop all services:
```
docker-compose down
```

### Deep clean:
```
docker-compose down -v
```

---

# Troubleshooting Guide

### **Port Conflicts**
Ports **8081–8083** must be free.  
If Tomcat cannot bind:
- Stop any Java processes  
- Or change ports in `application.properties` and `docker-compose.yml`

### **Network Timeouts**
If Booking Service fails early, wait 10 seconds and retry.  
Container startup times may differ.

### **JAR Not Found**
Rebuild services:

```
mvn clean package -DskipTests
```

---

# Technology Stack

| Category | Technology |
|----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Architecture | Microservices |
| Communication | REST |
| Databases | H2 (per service) |
| Build Tool | Maven |
| Containerization | Docker & Docker Compose |
| API Testing | Postman |

---
