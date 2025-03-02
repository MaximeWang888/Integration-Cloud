# Guide de démarrage local avec Docker Desktop et Kubernetes

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

### 3. Construire les images Docker spécifiques
```bash
# Construction des images pour les services spécifiques
make dc-build-specific SERVICES="authentification booking database"
```

### 4. Déployer les services
```bash
# Déployer en environnement de développement
make dev

# Vérifier que tous les pods sont démarrés
kubectl get pods -n development
```

### 5. Vérifier le déploiement
```bash
# Vérifier l'état global des services
make k8s-status

# Obtenir l'adresse IP du Gateway Istio
kubectl get svc istio-ingressgateway -n istio-system
```

### 6. Configurer l'accès local
```powershell
# Ajouter l'entrée DNS locale (exécuter PowerShell en tant qu'administrateur)
Add-Content -Path "$env:windir\System32\drivers\etc\hosts" -Value "`n127.0.0.1 microservice.local" -Force
```

### 7. Tester les endpoints
```bash
# Configurer le port forwarding pour le Gateway Istio
make k8s-port-forward

# Dans un autre terminal, tester les services
curl -H "Host: microservice.local" http://localhost:8080/api/auth/ping
curl -H "Host: microservice.local" http://localhost:8080/api/bookings/ping
curl -H "Host: microservice.local" http://localhost:8080/api/listings/ping
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

### Si Istio ne fonctionne pas
```bash
# Vérifier l'état du Gateway Istio
kubectl get pods -n istio-system

# Vérifier les logs du Gateway
kubectl logs -n istio-system -l app=istio-ingressgateway
```

## Commandes utiles
```bash
# Voir tous les services
make k8s-status

# Voir les logs d'un service spécifique
make k8s-logs service=<nom-du-service>

# Port forwarding pour accès local
make k8s-port-forward
```

## Arrêt de l'application
```bash
# Nettoyer toutes les ressources
make clean
``` 