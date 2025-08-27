FROM gradle:8.14.3-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM alpine/java:21
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE "${PORT-8080}"
ENTRYPOINT ["java", "-jar", "/app.jar"]