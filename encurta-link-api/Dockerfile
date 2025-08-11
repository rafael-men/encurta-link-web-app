FROM openjdk:17-jdk-slim as build

WORKDIR /app
COPY target/encurta-link-api-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8090
