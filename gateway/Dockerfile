# Multi-stage build. Stages are identified with the AS keyword.

# Stage 1 is the build stage and uses a JDK image to be able to build the project
FROM eclipse-temurin:21-jdk-alpine AS builder
# Copy entire project dir. Unnecessary files can be excluded with a .dockerignore
COPY . /app
# Set it as current working directory
WORKDIR /app
# Download dependencies separately to leverage layer caching (dependencies cached separately from the build)
RUN ./gradlew dependencies --no-daemon
RUN ./gradlew build --no-daemon

# Stage 2 is the runtime stage and produces a smaller image using the build from stage 1
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
