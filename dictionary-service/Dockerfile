FROM openjdk:11
EXPOSE 8089
ADD target/dictionary-service.jar dictionary-service.jar
ENTRYPOINT ["java","-jar","/dictionary-service.jar"]