# Step #1 - Build and Test
FROM gradle:6.8.3-jdk11 as Builder
COPY . /home
WORKDIR /home
RUN gradle build --info

# Step #2 - Creating Image
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=Builder /home/build/libs/*.jar app.jar
CMD ["java", "-jar", "/app/app.jar"]

