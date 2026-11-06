FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew
COPY src ./src
RUN ./gradlew --no-daemon bootJar

FROM eclipse-temurin:21-jre-jammy
RUN useradd --system --uid 10001 app && mkdir -p /app/uploads && chown -R app:app /app
WORKDIR /app
COPY --from=build --chown=app:app /build/build/libs/*-SNAPSHOT.jar app.jar
USER app
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=50.0"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
