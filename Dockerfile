# Multi-stage Dockerfile for payment-initiation-service
# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching for dependencies
COPY pom.xml .

# Download dependencies in a separate layer (cached if pom.xml doesn't change)
RUN mvn -B dependency:go-offline

# Copy source code and other necessary files
COPY src ./src
COPY openapi ./openapi
COPY config ./config

# Build the application (skip tests as they run in CI)
# The jar will be generated as: payment-initiation-service-0.0.1-SNAPSHOT.jar
# based on artifactId and version from pom.xml
RUN mvn -B clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the jar from builder stage and rename to app.jar for simplicity
# The jar name is: payment-initiation-service-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/target/payment-initiation-service-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables
# JAVA_OPTS can be overridden at runtime to add JVM flags (e.g., -Xmx512m)
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=docker

# Expose the application port (default Spring Boot port)
EXPOSE 8080

# Run the application
# Using sh -c to allow JAVA_OPTS to be expanded from environment variable
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

