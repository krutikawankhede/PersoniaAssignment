FROM openjdk:11
ADD target/personia-employee-service.jar personia-employee-service.jar
ADD personia.db personia.db
LABEL maintainer="Krutika Wankhede"

# Copy source code
COPY src /home/app/src
COPY pom.xml /home/app

# Expose ports
EXPOSE 8080

# Entry point
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=default", "personia-employee-service.jar"]


