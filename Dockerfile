FROM openjdk:8
ADD target/statistics-service.jar statistics-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","statistics-service.jar"]