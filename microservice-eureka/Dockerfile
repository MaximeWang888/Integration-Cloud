# Étape 1 : Construire le projet avec Maven
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app

# Copier les fichiers pom.xml et src dans l'image
COPY pom.xml .
COPY src ./src

# Télécharger les dépendances et construire le projet
RUN mvn clean package
RUN mvn dependency:copy-dependencies package -DskipTests

# Étape 2 : Exécuter l'application avec Java
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier les dépendances du projet Maven depuis l'étape de build
COPY --from=build /app/target/dependency/ ./

# Copier le JAR généré par Maven depuis l'étape précédente
COPY --from=build /app/target/microservice-eureka-0.0.1-SNAPSHOT.jar ./app.jar

# Exposer le port sur lequel l'application sera exécutée
EXPOSE 8080

# Définir le point d'entrée pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]