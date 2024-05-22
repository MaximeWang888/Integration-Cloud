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

<img width="589" alt="image" src="https://github.com/Fandresena02/MicroserviceAirBnB/assets/115694912/95c70765-a6be-4a08-b46d-eb41a3a2db8b">

## Prérequis

Avant d'installer et d'exécuter le projet, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop) (pour exécuter les services dans des conteneurs)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Installation

Pour installer et exécuter ce projet localement, suivez ces étapes :

1. Clonez le dépôt :
    ```bash
    git clone https://github.com/Fandresena02/MicroserviceAirBnB.git
    cd MicroserviceAirBnB
    ```

2. Construisez les microservices :
    ```bash
    mvn clean install
    ```

3. Pas de manuellement, utilisez Docker Compose pour lancer tous les microservices (Faites le si vous avez assez de CPU sinon étape 4.):
    ```bash
    docker-compose up --build
    ```
   
4.  Manuellement, lancez chaque main de chaque microservice pour lancer tous les microservices

## Usage

Une fois les microservices démarrés, vous pouvez accéder aux différentes fonctionnalités via leurs endpoints respectifs :

- Authentification : `http://localhost:8081`
- Réservation : `http://localhost:8082`
- Annonces : `http://localhost:8083`
- Suivi : `http://localhost:8084`
- Gestion des utilisateurs : `http://localhost:8085`

Pour vérifier si tout les microservices sont en service, lancez les commandes suivantes sur un terminal :
   ```bash
   curl http://localhost:8081/ping
   curl http://localhost:8082/ping
   curl http://localhost:8083/ping
   curl http://localhost:8084/ping
   curl http://localhost:8085/ping
   ```

## Configuration

Chaque microservice possède ses propres fichiers de configuration. Assurez-vous de consulter les fichiers `application.properties` ou `application.yml` dans chaque répertoire de microservice pour les configurations spécifiques.

## Contributions

Les contributions sont les bienvenues ! Veuillez soumettre des pull requests ou ouvrir des issues pour discuter des changements que vous souhaitez apporter.

## Licence

Ce projet est sous licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus de détails.

## Auteurs

- **Mohamad Reslan** - Développeur principal

## Remerciements

Merci à tous ceux qui ont contribué à ce projet.


Ajoutez ce contenu à votre fichier `README.md` pour refléter les dernières modifications et inclure les instructions pour exécuter les microservices via Docker Compose.
