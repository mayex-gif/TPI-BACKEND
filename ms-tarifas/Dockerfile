FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/ms-tarifas-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["sh", "-c", "sleep 30 && java -jar app.jar"]
