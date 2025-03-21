<div style="text-align: center;">
    <img src="images/blue-comet-logo.svg" alt="BlueComet Logo" style="width: 20%;" />
</div>

##### NOTE - *(Development in Progress)*
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
- **Database:** MySQL (current) -> PostgreSQL (will migrate)
- **Security:** Spring Security
- **Messaging:** Apache Kafka / RabbitMQ
- **API Design:** REST, GraphQL
- **Deployment:** Docker, Kubernetes (future roadmap)
- **Testing:** JUnit, Mockito, RESTAssured, Cucumber
- **Documentation:** Swagger, JavaDocs

## 🚀 Getting Started

### Prerequisites
- Java 21
- MySQL
- Maven (for dependency management)

### ⚙️ Setup & Installation
1. `Clone` / `Fork` the repository:

2. Set up environment variables (or use `application.properties`):
   ```properties
   # Spring Application Properties
   spring.application.name = event-planner
   
   # Spring Datasource Properties
   spring.datasource.url      = jdbc:mysql://127.0.0.1:3306/event_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username = root
   spring.datasource.password = root
   
   # Spring JPA Properties
   spring.jpa.database-platform  = org.hibernate.dialect.MySQLDialect
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql           = true
   
   # Server Properties
   server.port                 = 8080
   server.servlet.context-path = /api
   ```

4. ⚡ Run the application:


## 📖 API Documentation
For full API details, refer to **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## 📂 Project Structure
```
.
├── java
│   └── com
│       └── bluecomet
│           └── event_planner
│               ├── advice
│               │   └── GlobalExceptionHandler.java
│               ├── config
│               │   └── OpenAPIConfig.java
│               ├── EventPlannerApplication.java
│               ├── exception
│               │   ├── EventAlreadyCancelledException.java
│               │   ├── EventNotFoundException.java
│               │   ├── RegistrationAlreadyCancelledException.java
│               │   ├── RegistrationAlreadyExistsException.java
│               │   └── RegistrationNotFoundException.java
│               ├── mapper
│               │   ├── EventMapper.java
│               │   └── EventRegistrationMapper.java
│               ├── model
│               │   ├── entity
│               │   │   ├── Event.java
│               │   │   └── EventRegistration.java
│               │   ├── exchange
│               │   │   ├── ApiErrorResponse.java
│               │   │   ├── EventRegistrationRequest.java
│               │   │   ├── EventRegistrationResponse.java
│               │   │   ├── EventRequest.java
│               │   │   └── EventResponse.java
│               │   └── vo
│               │       ├── EventStatus.java
│               │       └── RegistrationStatus.java
│               ├── repository
│               │   ├── EventRegistrationRepository.java
│               │   └── EventRepository.java
│               ├── resource
│               │   ├── EventRegistrationResource.java
│               │   └── EventResource.java
│               ├── service
│               │   ├── api
│               │   │   └── EventRegistrationService.java
│               │   └── impl
│               │       ├── EventRegistrationServiceImpl.java
│               │       └── EventService.java
│               └── utils
│                   └── DateTimeUtils.java
└── resources
    ├── application.properties
    ├── schemas
    │   ├── event_registration.sql
    │   └── event.sql
    ├── static
    └── templates
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

