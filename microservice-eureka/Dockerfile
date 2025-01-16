# Étape 1 : Construire le projet avec Maven
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app

# Copier uniquement le fichier pom.xml pour mettre en cache les dépendances Maven si elles ne changent pas
COPY pom.xml .

# Télécharger les dépendances Maven sans construire le projet
RUN mvn dependency:go-offline -B

# Copier le reste des fichiers sources du projet
COPY src ./src

# Construire le projet et générer un fichier JAR sans exécuter les tests
RUN mvn clean package -DskipTests

# Étape 2 : Exécuter l'application avec Java
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier le fichier JAR généré par Maven depuis l'étape précédente
COPY --from=build /app/target/microservice-eureka-2.7.0.jar ./app.jar

# Exposer le port sur lequel Eureka Server écoute
EXPOSE 8761

# Définir le point d'entrée pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]