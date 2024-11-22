# Sử dụng OpenJDK 21 làm base image
FROM eclipse-temurin:21-jdk-alpine

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy file JAR từ quá trình build vào container
COPY target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8888

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
