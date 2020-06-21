FROM openjdk:8-jdk-slim as build

COPY build.gradle.kts settings.gradle.kts gradlew /usr/breddit-backend/
COPY gradle /usr/breddit-backend/gradle
WORKDIR /usr/breddit-backend
RUN ./gradlew --version

COPY . .
RUN ./gradlew --no-daemon clean build -x test

FROM openjdk:8-jre-slim
COPY --from=build /usr/breddit-backend/build/libs/* ./app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]
