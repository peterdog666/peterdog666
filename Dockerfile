FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/ai-app-1.0.0.jar app.jar
EXPOSE 8000
CMD ["java", "-jar", "app.jar", "--server.port=8000"]
