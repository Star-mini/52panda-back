FROM gradle:8.7.0-jdk17-alpine AS builder

WORKDIR /app
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Spring 소스 코드를 이미지에 복사
COPY . .

# Gradle 빌드 단계
RUN chmod +x ./gradlew && ./gradlew clean build

# 백엔드 서버 실행
FROM openjdk:17-alpine

WORKDIR /app

# 빌더 스테이지에서 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 백엔드 서버 실행
ENTRYPOINT ["nohup", "java", "-jar", "app.jar", "&"]
CMD ["--spring.profiles.active=dev"]
