FROM --platform=linux/amd64 gradle:8.7.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# TODO add gradle test or just remove -x test.
RUN gradle build --no-daemon -x test 

FROM --platform=linux/amd64 openjdk:17

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]