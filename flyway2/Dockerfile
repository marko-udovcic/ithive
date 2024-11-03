FROM openjdk:17-oracle
COPY target/flyway-0.0.1-SNAPSHOT.jar testapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","testapp.jar"]
