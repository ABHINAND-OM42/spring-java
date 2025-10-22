# Stage 1: Build Stage (Use a JDK image to build the application)
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app
# Copy the built JAR from your build directory (adjust path if using Gradle)
COPY target/*.jar app.jar

# Stage 2: Runtime Stage (Use a lightweight JRE for execution)

FROM eclipse-temurin:17-jre-focal
WORKDIR /app

#copy the jar from the build stage
COPY --from=build /app/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


