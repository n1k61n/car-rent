# 1. Merhele: Build
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
# Burada -Dmaven.test.skip=true əlavə etdik
RUN mvn clean package -Dmaven.test.skip=true

# 2. Merhele: Run
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]