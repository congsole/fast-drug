FROM openjdk:17 #base image
ARG JAR_FILE=build/libs/drug_app.jar #jar파일빌드해서 생성. jar파일을 이용해서 스프링부트를 띄운다.
COPY ${JAR_FILE} ./drug_app.jar #jar파일을컨테이너외부로카피
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./drug_app.jar"] #컨테이너내에서해당자바명령어를이용해서스프링부트를띄움
