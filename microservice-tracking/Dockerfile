# Étape 1 : Construire le projet avec Maven
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app

# Copier uniquement le pom.xml au début pour que Docker puisse mettre en cache cette étape si le pom.xml ne change pas
COPY pom.xml .

# Télécharger les dépendances Maven sans construire le projet (on cache cette étape)
RUN mvn dependency:go-offline -B

# Copier le reste des fichiers source après avoir téléchargé les dépendances
COPY src ./src

# Construire le projet en utilisant le cache de dépendances déjà téléchargées
RUN mvn clean package -DskipTests

# Étape 2 : Exécuter l'application avec Java
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier uniquement le JAR généré depuis l'étape de build
COPY --from=build /app/target/microservice-tracking-0.0.1-SNAPSHOT.jar ./app.jar

# Exposer le port sur lequel l'application sera exécutée
EXPOSE 8081

# Définir le point d'entrée pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]