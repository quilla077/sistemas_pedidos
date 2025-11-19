# Dockerfile
FROM eclipse-temurin:21-jdk-alpine
# FROM openjdk:21-jdk-slim

# Crear directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8088

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]