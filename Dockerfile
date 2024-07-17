FROM localhost:5000/openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8083

ENTRYPOINT ["java","-Djasypt.enc.key=noneuser","-jar","/app.jar"]