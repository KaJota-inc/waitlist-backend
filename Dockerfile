FROM eclipse-temurin:17-jdk-jammy
#FROM arm64v8/openjdk
ARG MONGO_URI
ENV MONGO_URI=${MONGO_URI}
COPY target/kajota-webpage-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


#Command for building and deploying
#docker buildx build --platform linux/amd64 -t kaota/kajota-waitlist-backend:latest --push .
