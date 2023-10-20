FROM amazoncorretto:17-alpine3.17-jdk
WORKDIR /app
COPY target/gatekeeper-service-0.0.1-SNAPSHOT.jar /app/gatekeeper-service.jar
EXPOSE 6000
CMD ["java","-jar","gatekeeper-service.jar"]