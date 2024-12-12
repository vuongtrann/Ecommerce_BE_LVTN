# Sử dụng OpenJDK 21 làm base image
FROM eclipse-temurin:21-jdk-alpine

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR vào container
COPY target/*.jar app.jar

# Chạy Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Cổng mà ứng dụng sẽ lắng nghe (có thể thay đổi tùy thuộc vào ứng dụng của bạn)
EXPOSE 8888