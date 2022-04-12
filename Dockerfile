FROM openjdk:11

MAINTAINER Bandisnc.inc

VOLUME /var/log
EXPOSE 8080

ARG JAR_FILE
ARG JVM_OPTIONS=$JVM_OPTIONS
ARG ACTIVE_PROFILE=$ACTIVE_PROFILE

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c" ,"java -Djava.security.egd=file:/dev/./urandom ${JVM_OPTIONS} -Dspring.profiles.active=${ACTIVE_PROFILE} -jar /app.jar"]