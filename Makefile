# Variables
DOCKER_COMPOSE = docker compose
KUBECTL = kubectl
PROFILE ?= all
NAMESPACE ?= development

# Colors for output
BLUE := \033[1;34m
GREEN := \033[1;32m
RED := \033[1;31m
RESET := \033[0m

# Docker Compose Commands
.PHONY: dc-build dc-up dc-down dc-logs dc-ps

dc-build:
	@echo "$(BLUE)Building Docker images with profile $(PROFILE)...$(RESET)"
	$(DOCKER_COMPOSE) --profile $(PROFILE) build

dc-up:
	@echo "$(BLUE)Starting services with profile $(PROFILE)...$(RESET)"
	$(DOCKER_COMPOSE) --profile $(PROFILE) up -d
	@echo "$(GREEN)Services started successfully!$(RESET)"

dc-down:
	@echo "$(BLUE)Stopping services...$(RESET)"
	$(DOCKER_COMPOSE) --profile $(PROFILE) down
	@echo "$(GREEN)Services stopped successfully!$(RESET)"

dc-logs:
	@echo "$(BLUE)Showing logs...$(RESET)"
	$(DOCKER_COMPOSE) --profile $(PROFILE) logs -f

dc-ps:
	@echo "$(BLUE)Showing running services...$(RESET)"
	$(DOCKER_COMPOSE) ps

# Kubernetes Commands
.PHONY: k8s-apply-all k8s-delete-all k8s-status k8s-logs k8s-port-forward

k8s-apply-all:
	@echo "$(BLUE)Applying all Kubernetes resources in namespace $(NAMESPACE)...$(RESET)"
	$(KUBECTL) create namespace $(NAMESPACE) --dry-run=client -o yaml | $(KUBECTL) apply -f -
	$(KUBECTL) apply -f kubernetes/database-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/authentification-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/booking-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/listing-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/tracking-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/user_management-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/ingress.yml -n $(NAMESPACE)
	@echo "$(GREEN)All resources applied successfully!$(RESET)"

k8s-delete-all:
	@echo "$(RED)Deleting all Kubernetes resources in namespace $(NAMESPACE)...$(RESET)"
	$(KUBECTL) delete -f kubernetes/ -n $(NAMESPACE) --ignore-not-found
	@echo "$(GREEN)All resources deleted successfully!$(RESET)"

k8s-status:
	@echo "$(BLUE)Checking Kubernetes resources status...$(RESET)"
	@echo "\nPods:"
	$(KUBECTL) get pods -n $(NAMESPACE)
	@echo "\nServices:"
	$(KUBECTL) get svc -n $(NAMESPACE)
	@echo "\nDeployments:"
	$(KUBECTL) get deployments -n $(NAMESPACE)

k8s-logs:
	@echo "$(BLUE)Fetching logs for service $(service)...$(RESET)"
	$(KUBECTL) logs -f deployment/$(service) -n $(NAMESPACE)

k8s-port-forward:
	@echo "$(BLUE)Setting up port forwarding for API Gateway...$(RESET)"
	$(KUBECTL) port-forward svc/api-gateway 8080:80 -n $(NAMESPACE)

# Ingress Commands
.PHONY: ingress-enable ingress-apply ingress-delete

ingress-enable:
	@echo "$(BLUE)Enabling Ingress Controller...$(RESET)"
	$(KUBECTL) apply -f kubernetes/ingress-infra.yml
	@echo "$(GREEN)Ingress Controller enabled!$(RESET)"

ingress-apply:
	@echo "$(BLUE)Applying Ingress configuration...$(RESET)"
	$(KUBECTL) apply -f kubernetes/ingress.yml -n $(NAMESPACE)
	@echo "$(GREEN)Ingress configuration applied!$(RESET)"
	@echo "$(BLUE)Adding microservice.local to hosts file...$(RESET)"
	@powershell -Command "Start-Process powershell -Verb RunAs -ArgumentList 'Add-Content -Path \"%WINDIR%\System32\drivers\etc\hosts\" -Value \"`n127.0.0.1 microservice.local`\" -Force'"
	@echo "$(GREEN)Host configuration completed!$(RESET)"

ingress-delete:
	@echo "$(RED)Deleting Ingress configuration...$(RESET)"
	$(KUBECTL) delete -f kubernetes/ingress.yml -n $(NAMESPACE) --ignore-not-found
	@echo "$(GREEN)Ingress configuration deleted!$(RESET)"

# Development Commands
.PHONY: dev prod clean ingress-setup deploy-env

# Détection du système d'exploitation
ifeq ($(OS),Windows_NT)
    SET_NAMESPACE = set NAMESPACE=$(1) &&
    RM_COLOR = 
    SLEEP = timeout /t $(1) /nobreak >nul
else
    SET_NAMESPACE = export NAMESPACE=$(1) &&
    RM_COLOR = \033[1;31m
    SLEEP = sleep $(1)
endif

.PHONY: dev prod clean ingress-setup deploy-env deploy-apps ingress-enable create-namespace clean-ingress check-cluster wait-ingress

# Vérification de la connexion au cluster
check-cluster:
	$(KUBECTL) cluster-info > nul 2>&1 || (echo "Erreur: Impossible de se connecter au cluster. Vérifiez que Docker Desktop/Minikube est démarré." && exit 1)

# Création du namespace
create-namespace:
	$(KUBECTL) create namespace $(NAMESPACE) --dry-run=client -o yaml --validate=false | $(KUBECTL) apply -f - --validate=false

# Nettoyage des Ingress existants
clean-ingress:
	-$(KUBECTL) delete -f kubernetes/ingress-infra.yml --ignore-not-found
	-$(KUBECTL) delete namespace ingress-nginx --ignore-not-found
	$(call SLEEP,10)

# Installation du contrôleur Nginx Ingress
ingress-setup:
	$(KUBECTL) apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.8.2/deploy/static/provider/cloud/deploy.yaml
	echo "Attente du démarrage du contrôleur Ingress..."
	$(call SLEEP,30)

# Attendre que le contrôleur Ingress soit prêt
wait-ingress:
	echo "Vérification du statut du contrôleur Ingress..."
	$(KUBECTL) wait --namespace ingress-nginx \
		--for=condition=ready pod \
		--selector=app.kubernetes.io/component=controller \
		--timeout=120s || (echo "Erreur: Le contrôleur Ingress n'est pas prêt. Vérifiez avec 'kubectl get pods -n ingress-nginx'" && exit 1)

# Déploiement des applications
deploy-apps:
	$(KUBECTL) apply -f kubernetes/database-deployment.yaml --validate=false
	$(call SLEEP,5)
	$(KUBECTL) apply -f kubernetes/authentification-deployment.yaml --validate=false
	$(KUBECTL) apply -f kubernetes/user_management-deployment.yaml --validate=false
	$(KUBECTL) apply -f kubernetes/booking-deployment.yaml --validate=false
	$(KUBECTL) apply -f kubernetes/listing-deployment.yaml --validate=false
	$(KUBECTL) apply -f kubernetes/tracking-deployment.yaml --validate=false
	$(call SLEEP,10)
	$(KUBECTL) apply -f kubernetes/ingress.yml --validate=false

# Configuration commune pour dev et prod
deploy-env:
	$(MAKE) check-cluster && \
	$(KUBECTL) config set-context --current --namespace=$(NAMESPACE) && \
	$(MAKE) create-namespace && \
	$(MAKE) clean-ingress && \
	$(MAKE) ingress-setup && \
	$(MAKE) wait-ingress && \
	$(MAKE) ingress-enable && \
	$(MAKE) deploy-apps

# Développement
dev:
	$(call SET_NAMESPACE,development) $(MAKE) deploy-env

# Production
prod:
	$(call SET_NAMESPACE,production) $(MAKE) deploy-env

# Nettoyage
clean:
	echo "$(RM_COLOR)Cleaning up resources...$(RM_COLOR)"
	-$(KUBECTL) delete namespace development --timeout=60s --ignore-not-found
	-$(KUBECTL) delete namespace production --timeout=60s --ignore-not-found
	-$(KUBECTL) delete namespace ingress-nginx --timeout=60s --ignore-not-found
	echo "Cleanup completed"

# Commandes communes
k8s-apply-all:
	$(KUBECTL) apply -f kubernetes/ --validate=false

ingress-enable:
	$(KUBECTL) apply -f kubernetes/ingress-infra.yml

ingress-apply:
	$(KUBECTL) apply -f kubernetes/ingress.yml

# Help
.PHONY: help
help:
	@echo "$(BLUE)Available commands:$(RESET)"
	@echo "$(GREEN)Docker Compose commands:$(RESET)"
	@echo "  dc-build         - Build Docker images"
	@echo "  dc-up           - Start services"
	@echo "  dc-down         - Stop services"
	@echo "  dc-logs         - Show service logs"
	@echo "  dc-ps           - List running services"
	@echo "\n$(GREEN)Kubernetes commands:$(RESET)"
	@echo "  k8s-apply-all   - Apply all K8s resources"
	@echo "  k8s-delete-all  - Delete all K8s resources"
	@echo "  k8s-status      - Show K8s resources status"
	@echo "  k8s-logs        - Show logs for a service"
	@echo "  k8s-port-forward- Forward API Gateway port"
	@echo "\n$(GREEN)Environment commands:$(RESET)"
	@echo "  dev            - Deploy to development"
	@echo "  prod           - Deploy to production"
	@echo "  clean          - Clean up all resources"
	@echo "\nUse PROFILE=<profile> to specify Docker Compose profile"
	@echo "Use NAMESPACE=<namespace> to specify Kubernetes namespace"

# Default target
.DEFAULT_GOAL := help
