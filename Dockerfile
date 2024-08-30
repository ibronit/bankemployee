FROM amazoncorretto:21 AS build

WORKDIR /app
COPY . .
RUN ./gradlew clean bootJar

FROM amazoncorretto:21 AS runtime

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]