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

- [Java 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop) (optionnel pour exécuter les services dans des conteneurs)

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

3. Lancez chaque microservice individuellement. Par exemple, pour le microservice d'authentification :
    ```bash
    cd microservice-authentification
    mvn spring-boot:run
    ```

## Usage

Une fois les microservices démarrés, vous pouvez accéder aux différentes fonctionnalités via leurs endpoints respectifs. Par exemple, les endpoints d'authentification pourraient être accessibles sur `http://localhost:8080`.

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
