# AgriConnect Backend

The Java backend service for **AgriConnect**, an agriculture-focused platform connecting farmers, students, and business users. It provides REST APIs for accounts, profiles, crop listings and orders, project collaboration, messaging, pesticide information, and dashboard data.

## Features
- User registration and login with JWT-based request authentication
- Farmer, student, and business profile workflows
- Crop listing and crop-order management
- Order conversations and project-specific chat
- Project collaboration and likes
- Pesticide information endpoints and initial CSV data loading
- Dashboard summaries and community statistics
- Profile-picture handling

## Tech Stack
- Java 21
- Spring Boot 4.1.0
- Spring Web MVC, Spring Data JPA, Hibernate
- Spring Security
- PostgreSQL
- JJWT 0.12.7
- Maven

## Project Structure
~~~text
src/main/java/com/agriconnect/
├── config/       # Security, CORS, web and data initialization
├── controller/  # REST API endpoints
├── dto/          # Request and response models
├── entity/       # JPA entities
├── exception/    # Exception handling
├── repository/   # Spring Data repositories
├── security/     # JWT authentication components
└── service/      # Application/business logic

src/main/resources/
├── application.properties
└── data/         # Initial pesticide CSV data
~~~

## Prerequisites
- JDK 21
- PostgreSQL
- Git

Maven Wrapper scripts are included.

## Environment Configuration
Set these environment variables before starting the service:

| Variable | Description |
|---|---|
| DATABASE_URL | PostgreSQL JDBC connection URL |
| DATABASE_USERNAME | Database username |
| DATABASE_PASSWORD | Database password |
| JWT_SECRET | Base64-encoded random secret, at least 32 bytes before encoding |
| PORT | Optional server port; defaults to 8080 |

Example local values (replace the placeholders):

~~~env
DATABASE_URL=jdbc:postgresql://localhost:5432/agriconnect
DATABASE_USERNAME=your_database_user
DATABASE_PASSWORD=your_database_password
JWT_SECRET=your_base64_encoded_random_secret
PORT=8080
~~~

Never commit real credentials. Store production values in your hosting provider's secret/environment settings.

## Run Locally
1. Clone this repository and enter its directory.
2. Create a PostgreSQL database.
3. Configure the environment variables above.
4. Start the service:

Windows:
~~~powershell
.\mvnw.cmd spring-boot:run
~~~

macOS/Linux:
~~~bash
./mvnw spring-boot:run
~~~

The default local port is 8080.

## Build and Test
~~~bash
./mvnw test
./mvnw package
~~~

On Windows, use mvnw.cmd.

## API Areas
The controllers currently cover:
- /api/users — registration, login, profile, and community statistics
- Farmer and business profile operations
- Crops and crop orders
- Order chat and project chat
- Projects and public messages
- Pesticides and dashboard data

See src/main/java/com/agriconnect/controller for the current routes and payloads.

## Notes
- A valid JWT_SECRET is required at runtime.
- Review JPA schema-management settings before connecting a production database.
- This documentation does not claim deployment or end-to-end test completion.

## License
No license file is currently included. All rights remain with the repository owner unless a license is added.
