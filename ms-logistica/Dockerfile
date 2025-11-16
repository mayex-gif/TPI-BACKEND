FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/ms-logistica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
