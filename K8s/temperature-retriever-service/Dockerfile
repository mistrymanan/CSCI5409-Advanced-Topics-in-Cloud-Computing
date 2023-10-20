FROM amazoncorretto:17-alpine3.17-jdk
WORKDIR /app
COPY target/temperature-retriever-service-0.0.1-SNAPSHOT.jar /app/temperature-retriever-service.jar
EXPOSE 6001
CMD ["java","-jar","temperature-retriever-service.jar"]