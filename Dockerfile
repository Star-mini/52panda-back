FROM gradle:8.7.0-jdk17-alpine

# 작업 디렉토리 설정
WORKDIR /app
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

RUN mkdir -p resources && \
    mkdir -p resources/images && \
    mkdir -p resources/logs
    

# gradle 초기화
RUN gradle init

# gradle wrapper를 프로젝트에 추가
RUN gradle wrapper

# gradlew를 이용한 프로젝트 필드
RUN chmod +x gradlew

# gradlew를 이용한 프로젝트 필드
RUN ./gradlew clean build 

# DATABASE_URL을 환경 변수로 삽입


# 빌드 결과 jar 파일을 실행
ENTRYPOINT ["nohup", "java", "-jar", "app.jar", "&"]
CMD ["--spring.profiles.active=dev"]
