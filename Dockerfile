FROM amazoncorretto:23-alpine-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/root.jar
COPY ${JAR_FILE} root.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=develop","-jar","/root.jar"]