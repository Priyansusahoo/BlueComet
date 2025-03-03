Structure of the project is as follows:

```
event-planner
│── src
│   ├── main
│   │   ├── java/com/bluecomet/eventplanner
│   │   │   ├── EventPlannerApplication.java  # Main Entry Point
│   │   │   ├── entity/                       # Model classes (Event, User, etc.)
│   │   │   ├── repository/                   # JPA Repositories
│   │   │   ├── service/                      # Business logic layer
│   │   │   ├── controller/                   # REST API controllers
│   │   │   ├── config/                       # Configuration files
│   │   │   ├── exception/                    # Custom exception handling
│   │   │   ├── dto/                          # Data Transfer Objects (DTOs)
│   │── resources/
│   │   ├── application.properties            # Configuration File
│   │   ├── static/                           # Static assets (CSS, JS, images)
│   │   ├── templates/
```  

