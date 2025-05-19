FROM amazoncorretto:21 AS build

WORKDIR /app
COPY . .

EXPOSE 8080

ENTRYPOINT ["./gradlew", "clean", "bootRun"]