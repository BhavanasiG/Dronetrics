FROM eclipse-temurin:26-jdk AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN sed -i 's/\r$//' gradlew
RUN chmod +x gradlew

RUN ./gradlew build -x test --no-daemon || true

COPY src src
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:26-jre
WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

COPY --from=build /app/build/libs/*.jar app.jar

ENV SERVER_PORT=2004
EXPOSE 2004

ENTRYPOINT ["java", "-jar", "app.jar"]