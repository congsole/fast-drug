FROM openjdk:17
ARG JAR_FILE=build/libs/drug_app.jar
COPY ${JAR_FILE} ./drug_app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./drug_app.jar"]
