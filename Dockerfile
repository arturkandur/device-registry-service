FROM amazoncorretto:25
WORKDIR /app
COPY device-registry-service/target/device-registry-service-*.jar app.jar
USER 1001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]