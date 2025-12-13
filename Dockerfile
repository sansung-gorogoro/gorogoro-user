FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/auth-0.0.1-SNAPSHOT.jar user.jar

ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/user.jar"]
