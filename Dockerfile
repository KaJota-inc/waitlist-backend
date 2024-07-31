FROM eclipse-temurin:21
ARG JAR_FILE=target/*.jar
ARG MONGO_URI
ENV MONGO_URI=${MONGO_URI}
COPY ./target/kajota-webpage-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]