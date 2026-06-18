# Dockerfile for techwatch-backend
# Summary:
#   Multi-stage build that compiles the Spring Boot application with Maven (JDK 26)
#   and runs the resulting JAR on a slim Temurin 26 JRE.
# Description:
#   The build stage uses the Maven image's bundled Maven (matches the project's
#   wrapper version 3.9.x) so no wrapper download is needed inside the container.
#   The runtime stage runs with the 'prod' Spring profile and exposes port 8080.
# Version: 1.0
# Maintainer: TechWatch Development Team

# Step 1: Build the application using Maven
FROM maven:3.9.16-eclipse-temurin-26 AS build
WORKDIR /app
# Resolve dependencies first so this layer is cached across code-only changes
COPY pom.xml .
RUN mvn -B dependency:go-offline
# Copy sources and build the executable JAR
COPY src ./src
RUN mvn -B clean package -DskipTests

# Step 2: Create a lightweight runtime image
FROM eclipse-temurin:26-jre AS runtime
ENV SPRING_PROFILES_ACTIVE=prod
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Step 3: Configure and run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Note: The application runs with the 'prod' profile set above.
# Required environment variables in the hosting provider (Railway):
# - DATABASE_URL: database host (e.g. mysql.railway.internal)
# - DATABASE_PORT: database port (default 3306)
# - DATABASE_NAME: database name
# - DATABASE_USER: database username
# - DATABASE_PASSWORD: database password
# - PORT: port the app listens on (Railway injects this; defaults to 8080)
# - SPRING_PROFILES_ACTIVE: must be 'prod'
