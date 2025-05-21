# VPPSystem
# 🔌 Virtual Power Plant (VPP) Aggregator System – REST API with Spring Boot


This project implements a RESTful API system for managing a **Virtual Power Plant (VPP)**.
The VPP aggregates distributed battery resources, allowing small-scale energy contributors 
to operate collectively as a single power provider.

---

### 📘 Features

- Save a list of PowerCell with name, postcode, and capacity
- Retrieve PowerCell by postcode range
- Calculate total and average capacity for PowerCell in a range
- Containerized integration testing with Docker
- Code coverage and quality check via Jacoco and Maven

---

## 🛠️ Tech Stack

- **Java 17**
- **SpringBoot 3.3.11**
- **MySQL**
- **Hibernate (JPA)**
- **JUnit 5**
- **Testcontainers**
- **Jacoco**
- **Postman**
- **Docker**

---

## 🚀 Getting Started


## Architectural Design

- Spring Boot Application for minimal configuration requirements,Embedded tomcatserver and production ready features 
  like Health metrics checkup,Logging & Tracing support,Externalized configuration.
- Choosen Monoloithic Application over Microservice because of Simple and Faster development,Lower complexities for inter service communication,Single codebase,Easier Testing & Deployment.
- MVC Architecture more readable and maintainable,seperate business logic,Easier to test and debug.
- Used MYSQL Database because of light weight,less complexity on setup,and Faster for Read heavy and simple write operations.

### ✅ Prerequisites

- Java 17+
- Maven 3.8.1
- MySQL 8.0
- Docker (for test cases)
- Github

### 🔧 Setup Instructions

1. **Clone the Repository**
   git clone git@github.com:Amit-Dhakal/VPPSystem.git (ssh)
   git clone https://github.com/Amit-Dhakal/VPPSystem.git (https)

3. ** Start Docker**
   creates mysql container for Data Jpa Test case

4. ** Run Application Main **
    start VppSystemApplication.java
    or mvn clean spring-boot:run (commandline)

5. ** Run TestApplication **
    start VppSystemApplicationTests.java

6. ** Code Coverage Report **
   mvn clean install (creates target folder if missing)
   mvn clean verify
   target/site/index.html
   open index.html file on web browser



 ************ END **********
