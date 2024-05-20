# Utiliser l'image de base officielle OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR de l'application dans le conteneur
COPY target/microservice-booking-0.0.1-SNAPSHOT.jar /app/microservice-booking-0.0.1-SNAPSHOT.jar

# Exposer le port que l'application utilisera
EXPOSE 8082

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "microservice-booking-0.0.1-SNAPSHOT.jar"]
