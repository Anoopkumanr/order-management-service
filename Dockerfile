FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Copy source code
COPY src ./src

# Build Spring Boot application
RUN mvn clean package -DskipTests


# =========================
# Stage 2: Run Application
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create non-root user
RUN groupadd spring && useradd -g spring spring

# Copy generated JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Give ownership to spring user
RUN chown -R spring:spring /app

# Run container as non-root user
USER spring

# Spring Boot default port
EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]
