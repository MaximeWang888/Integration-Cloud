# Utiliser l'image de base officielle OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR de l'application dans le conteneur
COPY target/microservice-management-0.0.1-SNAPSHOT.jar /app/microservice-management-0.0.1-SNAPSHOT.jar

# Exposer le port que l'application utilisera
EXPOSE 8081

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "microservice-management-0.0.1-SNAPSHOT.jar"]
