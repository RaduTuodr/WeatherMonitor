FROM maven:3.8.6-openjdk-17-slim as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn clean install -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/WeatherMonitor-0.0.1-SNAPSHOT.jar /app/your-app.jar
EXPOSE 8080
CMD ["java", "-jar", "your-app.jar"]
