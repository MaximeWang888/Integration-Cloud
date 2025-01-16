# Utilise l'image MySQL officielle
FROM mysql:latest

# Copie le script d'initialisation SQL dans le conteneur
COPY init.sql /docker-entrypoint-initdb.d/

# Définit les variables d'environnement pour autoriser l'accès à tous
ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_DATABASE=microservice_db
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password