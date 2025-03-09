<div style="text-align: center;">
    <img src="images/blue-comet-logo.svg" alt="BlueComet Logo" style="width: 20%;" />


<!-- ## Event Management System *(Development in Progress)* -->
</div>

## Overview

The `BlueComet` is a scalable Event-Management-System, secure platform designed for organizations to manage event registrations efficiently. The system allows users to register for events, while admins can create, update, and cancel events. It follows industry best practices, leveraging modern technologies to ensure high performance and security.

## 💡 Features
    ⏳ Secure authentication with Keycloak (OAuth 2.0, OpenID Connect) - 
    ✅ RESTful API design with industry-standard best practices
    ⏳ GraphQL support for optimized data fetching
    ⏳ Event-driven architecture using Kafka for real-time notifications
    ✅ Comprehensive logging, error handling, and validation
    ✅ Swagger API documentation & JavaDocs for maintainability
    ✅ Scalable with MySQL as the database
    ⏳ Dockerized for easy deployment

## 🛠️ Tech Stack - *Can change based on requirements*
- **Backend:** Java, Spring (future roadmap)
- **Database:** MySQL (will migrate to PostgreSQL)
- **Security:** Spring Security
- **Messaging:** Apache Kafka / RabbitMQ -> Not Decided
- **API Design:** REST, GraphQL
- **Deployment:** Docker, Kubernetes (future roadmap)
- **Testing:** JUnit, Mockito
- **Documentation:** Swagger, JavaDocs

## 🚀 Getting Started

### Prerequisites
- Java 17+
- MySQL
- Maven (for dependency management)

### ⚙️ Setup & Installation
1. `Clone` / `Fork` the repository:

2. Set up environment variables (or use `application.properties`):
   ```sh
    spring.application.name=event-planner


    # ===============================
    # = DATABASE CONFIGURATION =
    # ===============================
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/event_db?useSSL=false&serverTimezone=UTC
    spring.datasource.username=your_username
    spring.datasource.password=your_pasword
    
    # ===============================
    # = JPA CONFIGURATION =
    # ===============================
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
   ```

4. ⚡ Run the application:

## 🌐 API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/api/events` | Create a new event |
| `GET`  | `/api/events/{id}` | Retrieve event details |
| `PUT`  | `/api/events/{id}` | Update an event |
| `DELETE` | `/api/events/{id}` | Cancel an event |
| `POST` | `/api/registrations` | Register a user for an event |
| `DELETE` | `/api/registrations/{userId}/{eventId}` | Cancel registration |

And more...

## 📖 API Documentation
For full API details, refer to **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## 📂 Project Structure
```
📂 src
 ┣ 📂 main
 ┃ ┣ 📂 java
 ┃ ┃ ┣ 📂 com.example.event
 ┃ ┃ ┃ ┣ 📂 config       # configuration classes
 ┃ ┃ ┃ ┣ 📂 controller   # API Controllers
 ┃ ┃ ┃ ┣ 📂 dto          # Data Transfer Objects
 ┃ ┃ ┃ ┣ 📂 entity       # Entities
 ┃ ┃ ┃ ┣ 📂 enums        # Enum
 ┃ ┃ ┃ ┣ 📂 repository   # Database Access Layer
 ┃ ┃ ┃ ┣ 📂 service      # Business Logic
 ┃ ┃ ┃ ┣ 📂 utils        # Utility classes
 ┃ ┣ 📂 resources
 ┃ ┃ ┣ 📜 application.properties  # Configurations
 ┣ 📂 test  # Unit & Integration Tests
```

## 🗺️ Roadmap
- ⏳ **MVP: Basic Event System** (Current Phase)
- 🔜 **Version 2: Payment Integration (Stripe, Razorpay)**
- 🔜 **Version 3: Microservices Architecture & Event Analytics**
- 🔜 **Version 4: AI-Powered Event Recommendations**

## 🤝 Contributing
1. Fork the repo & create a feature branch
2. Follow **Clean-Modular Code** & **SOLID principles**
3. Submit a PR with detailed descriptions

## 📜 License
[MIT License](LICENSE)

## 📧 Contact
- For a new `Feature` or `bugs` create a issue in `Issue` tab
- For queries, reach out at [bluecomet.org@gmail.com](mailto:bluecomet.org@gmail.com)

