# MicroserviceAirBnB

Ce projet implémente une architecture de microservices pour une application de type Airbnb. Chaque microservice est responsable d'une partie spécifique du système global.

## Table des matières

- [Introduction](#introduction)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributions](#contributions)
- [Licence](#licence)
- [Auteurs](#auteurs)
- [Remerciements](#remerciements)

## Introduction

Le projet MicroserviceAirBnB vise à fournir une solution de réservation d'hébergements en utilisant une architecture de microservices. Chaque microservice est isolé et gère une partie spécifique du système, comme l'authentification, la réservation, la gestion des annonces, le suivi et la gestion des utilisateurs.

## Architecture

Le projet est divisé en plusieurs microservices :

- **microservice-authentification** : Gère l'authentification et l'autorisation des utilisateurs.
- **microservice-booking** : Gère le processus de réservation des hébergements.
- **microservice-listing** : Gère les annonces d'hébergements.
- **microservice-tracking** : Gère le suivi des activités et des événements.
- **microservice-user_management** : Gère les informations et les profils des utilisateurs.

![Clone Airbnb's Architecture](https://github.com/Fandresena02/MicroserviceAirBnB/assets/75336673/c65caa54-8592-4f1d-8b4b-7a1f26c9df8d)

## Prérequis

Avant d'installer et d'exécuter le projet, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop) (pour exécuter les services dans des conteneurs)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Make](https://gnuwin32.sourceforge.net/packages/make.htm)
 (pour lancer les commandes plus facilement)

## Installation

Pour installer et exécuter ce projet localement, suivez ces étapes :

1. Clonez le dépôt :
    ```bash
    git clone https://github.com/MaximeWang888/Microservice-Airbnb.git
    cd Microservice-Airbnb
    ```

2. Construisez les microservices :
    ```bash
    mvn clean install
    ```

3. Utilisez Docker Compose pour construire toutes les images de chaque microservices :
    ```bash
    make build profile=infra
    ```
   
4.  Utilisez Docker Compose pour lancer tous les containers sous docker :
    ```bash
    make build profile=microservice
    ```

## Usage

Une fois les microservices démarrés, vous pouvez accéder aux différentes fonctionnalités via leurs endpoints respectifs :

- Authentification : `http://localhost:8081`
- Réservation : `http://localhost:8082`
- Annonces : `http://localhost:8083`
- Suivi : `http://localhost:8084`
- Gestion des utilisateurs : `http://localhost:8085`

Pour vérifier si tout les microservices sont en service, lancez les commandes suivantes sur un terminal :
   ```bash
   curl http://localhost:8081/auth/ping
   curl http://localhost:8082/bookings/ping
   curl http://localhost:8083/listings/ping
   curl http://localhost:8084/tracking/ping
   curl http://localhost:8085/users/ping
   ```

## Configuration

Chaque microservice possède ses propres fichiers de configuration. Assurez-vous de consulter les fichiers `application.properties` ou `application.yml` dans chaque répertoire de microservice pour les configurations spécifiques.

## Contributions

Les contributions sont les bienvenues ! Veuillez soumettre des pull requests ou ouvrir des issues pour discuter des changements que vous souhaitez apporter.

## Licence

Ce projet est sous licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus de détails.

## Remerciements

Merci à tous ceux qui ont contribué à ce projet.


Ajoutez ce contenu à votre fichier `README.md` pour refléter les dernières modifications et inclure les instructions pour exécuter les microservices via Docker Compose.