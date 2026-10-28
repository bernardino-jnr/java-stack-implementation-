FROM openjdk:17-jdk-slim
WORKDIR /app
COPY application.java /app
RUN javac application.java
EXPOSE 8080
CMD ["java", "application"]
