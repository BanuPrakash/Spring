FROM openjdk:17

EXPOSE 8080
VOLUME /tmp

WORKDIR /app

COPY ./gradlew/ ./gradlew
COPY build.gradle ./
#RUN ./gradlew dependency:resolve

COPY src ./src

CMD ["./gradlew", "spring-boot:run"]