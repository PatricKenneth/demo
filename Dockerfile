FROM openjdk:17.0-slim-buster

WORKDIR /app

ENV TZ="America/Sao_Paulo"

ARG PROFILE

ENV PROJ_PROFILE=${PROFILE}

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROJ_PROFILE}", "-jar", "/app/demo.jar"]

VOLUME ["/app/config"]

COPY /target/demo.jar .