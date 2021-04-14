FROM openjdk:11
EXPOSE 8088
ADD target/book-service.jar book-service.jar
ENTRYPOINT ["java","-jar","/book-service.jar"]