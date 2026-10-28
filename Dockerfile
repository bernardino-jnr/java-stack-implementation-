FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY Application.java /app/Application.java
RUN javac Application.java
EXPOSE 8080
CMD ["java", "Application"]
