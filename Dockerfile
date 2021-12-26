# 실행(.까지임)
# docker build -t [dockerHub 계정]/[dockerHub에 만들어둔 repository name] .

# openjdk 11 적용
FROM openjdk:11-jdk

# build 시점에 활용되는 변수선언문
ARG JAR_FILE=target/*.jar

# app.jar로 복사
COPY ${JAR_FILE} app.jar

# 현재 경로기준에서 target안에 있는 jar파일을 복사해서 app.jar파일로 만들어서 이미지에 적재한다.
#ADD target/app-0.0.1-SNAPSHOT.jar app.jar

# 환경변수
ENV JAVA_OPTS=""

# 이미지가 동작하면서 실행시킬 명령어
# 기존 java -jar target/app-0.0.1-SNAPSHOT.jar 명령어로 실행하듯이 해줌
ENTRYPOINT ["java","-jar","/app.jar"]

# 생성 시 Port
EXPOSE 3030


