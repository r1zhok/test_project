FROM openjdk:21
ENV PORT 8080
EXPOSE 8080
COPY target/*.jar spring-app.jar
ENTRYPOINT ["java","-jar","/spring-app.jar"]