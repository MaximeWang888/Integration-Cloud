# Microservice Airbnb

Ce projet implémente une architecture de microservices pour une application de type Airbnb, utilisant Spring Cloud Gateway et Kubernetes pour la gestion des services.

## Table des matières

- [Introduction](#introduction)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributions](#contributions)
- [Licence](#licence)

## Introduction

Le projet Microservice Airbnb fournit une solution de réservation d'hébergements basée sur une architecture microservices moderne. L'application utilise Spring Cloud Gateway comme point d'entrée unique et Kubernetes pour l'orchestration des conteneurs, offrant une scalabilité automatique et une haute disponibilité.

## Architecture

Le projet est composé des éléments suivants :

### Services Principaux
- **API Gateway** : Point d'entrée unique gérant le routage et la sécurité
- **Database** : Base de données MySQL centralisée
- **Authentification** : Gestion de l'authentification et autorisation
- **Booking** : Gestion des réservations
- **Listing** : Gestion des annonces
- **Tracking** : Suivi des activités
- **User Management** : Gestion des utilisateurs

### Architecture Technique
```
                   Internet
                      │
                      ▼
              [LoadBalancer]
                      │
                      ▼
              [API Gateway]
                      │
         ┌────────────┴───────────┐
         │           │            │
         ▼           ▼            ▼
   [ClusterIP]  [ClusterIP]  [ClusterIP]
   [Services]   [Services]   [Services]
         │           │            │
         └────────── ▼ ───────────┘
              [Database]
```

## Prérequis

- [Docker](https://www.docker.com/products/docker-desktop)
- [Kubernetes](https://kubernetes.io/) (activé dans Docker Desktop)
- [kubectl](https://kubernetes.io/docs/tasks/tools/)
- [Make](https://www.gnu.org/software/make/)
- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)

## Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/votre-username/Microservice-Airbnb.git
cd Microservice-Airbnb
```

2. Construction des images Docker :
```bash
make dc-build PROFILE=all
```

3. Déploiement sur Kubernetes :
```bash
# Environnement de développement
make dev

# Environnement de production
make prod
```

## Usage

### Docker Compose (Développement local)
```bash
# Démarrer tous les services
make dc-up PROFILE=all

# Démarrer uniquement les microservices
make dc-up PROFILE=microservice

# Arrêter les services
make dc-down
```

### Kubernetes
```bash
# Vérifier l'état des services
make k8s-status

# Voir les logs
make k8s-logs service=api-gateway

# Port-forward pour accès local
make k8s-port-forward
```

### Endpoints API
Tous les services sont accessibles via l'API Gateway :
- Authentication : `http://localhost/api/auth/**`
- Booking : `http://localhost/api/bookings/**`
- Listing : `http://localhost/api/listings/**`
- Tracking : `http://localhost/api/tracking/**`
- User Management : `http://localhost/api/users/**`

## Configuration

### Environnements
- **Development** : `make dev`
- **Production** : `make prod`

### Variables d'environnement importantes
```yaml
SPRING_PROFILES_ACTIVE: prod
JAVA_OPTS: -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0
```

## Monitoring et Maintenance

### Healthchecks
Tous les services incluent des endpoints de santé :
```bash
curl http://localhost/actuator/health
```

### Ressources
Chaque service est configuré avec :
- Limites de mémoire : 512M
- Healthchecks automatiques
- Redémarrage automatique en cas d'échec

## Configuration Ingress

L'application utilise Nginx Ingress Controller pour gérer le routage du trafic entrant.

### Configuration locale

1. Activer l'Ingress Controller :
```bash
make ingress-enable
```

2. Appliquer la configuration Ingress :
```bash
make ingress-apply
```

3. Ajouter l'entrée DNS locale (nécessite les droits sudo) :
```bash
echo "127.0.0.1 microservice.local" | sudo tee -a /etc/hosts
```

### Accès aux services

Une fois configuré, les services sont accessibles via :

- Authentication : `http://microservice.local/api/auth/**`
- Booking : `http://microservice.local/api/bookings/**`
- Listing : `http://microservice.local/api/listings/**`
- Tracking : `http://microservice.local/api/tracking/**`
- User Management : `http://microservice.local/api/users/**`

### Vérification

Pour vérifier que l'Ingress fonctionne :

```bash
# Vérifier le statut de l'Ingress
kubectl get ingress -n development

# Tester un endpoint
curl -H "Host: microservice.local" http://localhost/api/auth/ping
```

### Dépannage Ingress

Si l'Ingress ne fonctionne pas :

1. Vérifier que l'Ingress Controller est actif :
```bash
kubectl get pods -n ingress-nginx
```

2. Vérifier les logs de l'Ingress Controller :
```bash
kubectl logs -n ingress-nginx deployment/ingress-nginx-controller
```

3. Vérifier la configuration :
```bash
kubectl describe ingress microservice-ingress -n development
```

# Guide de démarrage local

## Étapes de lancement

### 1. Cloner le projet
```bash
git clone https://github.com/votre-username/Microservice-Airbnb.git
cd Microservice-Airbnb
```

### 2. Construire les images Docker
```bash
# Construction de toutes les images
make dc-build PROFILE=all
```

### 3. Démarrer l'environnement de développement
```bash
# Option 1 : Avec Docker Compose (recommandé pour le développement)
make dc-up PROFILE=all

# Option 2 : Avec Kubernetes
make dev
```

### 4. Vérifier que tout fonctionne

a) Vérifier les services Docker Compose :
```bash
make dc-ps
```

b) Vérifier les services Kubernetes :
```bash
make k8s-status
```

c) Tester les endpoints via l'API Gateway :
```bash
# Test de l'authentification
curl http://localhost/api/auth/ping

# Test des réservations
curl http://localhost/api/bookings/ping

# Test des annonces
curl http://localhost/api/listings/ping
```

### 5. Consulter les logs
```bash
# Logs Docker Compose
make dc-logs

# Logs Kubernetes d'un service spécifique
make k8s-logs service=api-gateway
```

### 6. Arrêter l'application
```bash
# Arrêt Docker Compose
make dc-down

# Ou arrêt Kubernetes
make clean
```

## Ports utilisés
- API Gateway : 80 (externe), 8080 (interne)
- Base de données : 3306
- Services :
  - Authentification : 8081
  - Booking : 8082
  - Listing : 8083
  - Tracking : 8084
  - User Management : 8085

## Résolution des problèmes courants

1. **Les services ne démarrent pas**
   ```bash
   # Vérifier les logs
   make dc-logs
   # ou
   make k8s-logs service=nom-du-service
   ```

2. **Problème de connexion à la base de données**
   ```bash
   # Vérifier que la base de données est bien démarrée
   docker ps | grep database
   ```

3. **L'API Gateway n'est pas accessible**
   ```bash
   # Vérifier le port forwarding
   make k8s-port-forward
   ```

4. **Réinitialisation complète**
   ```bash
   # Nettoyer tout et redémarrer
   make clean
   make dc-build PROFILE=all
   make dc-up PROFILE=all
   ```

## Développement

Pour travailler sur un service spécifique :
1. Modifier le code source
2. Reconstruire l'image :
   ```bash
   make dc-build PROFILE=microservice
   ```
3. Redémarrer le service :
   ```bash
   make dc-up PROFILE=microservice
   ```

## Monitoring

Accéder aux métriques des services :
```bash
# Métriques de l'API Gateway
curl http://localhost/actuator/metrics

# Santé des services
curl http://localhost/actuator/health
```

## Contributions

Les contributions sont les bienvenues ! Veuillez :
1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## Licence

Distribué sous la licence MIT. Voir `LICENSE` pour plus d'informations.