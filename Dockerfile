FROM openjdk:16
EXPOSE 8080
ADD target/diploma.jar diploma.jar
ENTRYPOINT ["java", "-jar", "/diploma.jar"]