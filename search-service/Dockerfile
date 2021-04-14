FROM openjdk:11
EXPOSE 8087
ADD target/search-service.jar search-service.jar
ENTRYPOINT ["java","-jar","/search-service.jar"]