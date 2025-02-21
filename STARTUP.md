# Guide de démarrage local avec Docker Desktop Kubernetes

## Prérequis
1. Docker Desktop installé et démarré
2. Kubernetes activé dans Docker Desktop (Settings > Kubernetes > Enable Kubernetes)
3. kubectl installé
4. Make installé

## Étapes de démarrage

### 1. Cloner le projet
```bash
git clone <votre-repo>
cd <votre-repo>
```

### 2. Construire les images Docker
```bash
# Construction de toutes les images
make dc-build PROFILE=all
```

### 3. Activer Ingress sur Docker Desktop
```bash
# Installer l'Ingress Controller
kubectl apply -f kubernetes/ingress-infra.yml

# Vérifier que l'Ingress Controller est bien démarré
kubectl get pods -n ingress-nginx
```

### 4. Déployer les services
```bash
# Déployer en environnement de développement
make dev

# Vérifier que tous les pods sont démarrés
kubectl get pods -n development
```

### 5. Configurer l'accès local
```powershell
# Ajouter l'entrée DNS locale (exécuter PowerShell en tant qu'administrateur)
Add-Content -Path "$env:windir\System32\drivers\etc\hosts" -Value "`n127.0.0.1 microservice.local" -Force
```

### 6. Tester les endpoints
```bash
# Test du service d'authentification
curl -H "Host: microservice.local" http://localhost/api/auth/ping

# Test du service de réservation
curl -H "Host: microservice.local" http://localhost/api/bookings/ping

# Test du service de listing
curl -H "Host: microservice.local" http://localhost/api/listings/ping
```

## Résolution des problèmes courants

### Si les pods ne démarrent pas
```bash
# Vérifier l'état des pods
kubectl get pods -n development

# Vérifier l'état détaillé d'un pod spécifique
kubectl describe pod -n development <nom-du-pod>

# Voir les logs d'un pod
kubectl logs -n development <nom-du-pod>
```

### Si l'Ingress ne fonctionne pas
```bash
# Vérifier l'état de l'Ingress
kubectl get ingress -n development

# Vérifier la configuration détaillée
kubectl describe ingress -n development

# Vérifier les logs de l'Ingress Controller
kubectl logs -n ingress-nginx -l app.kubernetes.io/component=controller
```

## Arrêt de l'application
```bash
# Nettoyer toutes les ressources
make clean
```

## Commandes utiles
```bash
# Voir tous les services
kubectl get services -A

# Voir tous les pods dans tous les namespaces
kubectl get pods -A

# Voir les événements en temps réel
kubectl get events -n development --watch
``` 