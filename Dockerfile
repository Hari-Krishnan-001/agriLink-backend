# ========================================
# BUILD STAGE
# ========================================

FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration first
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests


# ========================================
# RUN STAGE
# ========================================

FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy generated JAR
COPY --from=build /app/target/*.jar app.jar

# Render provides the PORT environment variable
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]