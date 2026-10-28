FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY application.java /app/application.java
RUN javac application.java
EXPOSE 8080
CMD ["java", "application"]
