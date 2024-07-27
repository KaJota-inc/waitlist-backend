FROM eclipse-temurin:21
ARG JAR_FILE=target/*.jar
COPY ./target/kajota-webpage-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]