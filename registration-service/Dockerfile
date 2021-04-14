FROM openjdk:11
EXPOSE 8082
ADD target/registration-service.jar registration-service.jar
ENTRYPOINT ["java","-jar","/registration-service.jar"]