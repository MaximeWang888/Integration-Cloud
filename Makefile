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
.PHONY: dc-build dc-up dc-down dc-logs dc-ps dc-build-specific

dc-build:
	@echo "$(BLUE)Building Docker images with profile $(PROFILE)...$(RESET)"
	$(DOCKER_COMPOSE) --profile $(PROFILE) build

dc-build-specific:
	@echo "$(BLUE)Building Docker images for specific services: $(SERVICES)...$(RESET)"
	$(DOCKER_COMPOSE) build $(SERVICES)

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
.PHONY: k8s-apply-all k8s-delete-all k8s-status k8s-logs k8s-port-forward setup-istio clean-istio

setup-istio:
	@echo "$(BLUE)Installing Istio with Helm...$(RESET)"
	helm repo add istio https://istio-release.storage.googleapis.com/charts
	helm repo update
	kubectl create namespace istio-system --dry-run=client -o yaml | kubectl apply -f -
	-helm install istio-base istio/base -n istio-system --set enableCRDTemplates=true || true
	-helm install istiod istio/istiod -n istio-system --wait || true
	-helm install istio-ingress istio/gateway -n istio-system || true
	@echo "Waiting for Istio to be ready..."
	kubectl wait --for=condition=ready pod --all -n istio-system --timeout=300s
	kubectl label namespace $(NAMESPACE) istio-injection=enabled --overwrite
	@echo "$(GREEN)Istio installed successfully!$(RESET)"
		
	@echo "$(BLUE)Applying Istio Gateway configuration...$(RESET)"
	helm upgrade --install istio-gateway ./istio-gateway --namespace $(NAMESPACE) --set namespace=$(NAMESPACE)
	@echo "$(GREEN)Istio Gateway applied successfully!$(RESET)"

clean-istio:
	@echo "$(BLUE)Cleaning up Istio...$(RESET)"
	-helm uninstall istio-ingress -n istio-system
	-helm uninstall istiod -n istio-system
	-helm uninstall istio-base -n istio-system
	-kubectl delete namespace istio-system --ignore-not-found
	@echo "$(GREEN)Istio cleaned up successfully!$(RESET)"

k8s-apply-all: setup-istio
	@echo "$(BLUE)Applying all Kubernetes resources in namespace $(NAMESPACE)...$(RESET)"
	$(KUBECTL) create namespace $(NAMESPACE) --dry-run=client -o yaml | $(KUBECTL) apply -f -
	$(KUBECTL) apply -f kubernetes/database-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/authentification-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/booking-deployment.yaml -n $(NAMESPACE)
	$(KUBECTL) apply -f kubernetes/istio-gateway.yaml -n $(NAMESPACE)
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
	@echo "\nIstio Gateway:"
	$(KUBECTL) get gateway -n $(NAMESPACE)
	@echo "\nVirtual Services:"
	$(KUBECTL) get virtualservices -n $(NAMESPACE)

k8s-logs:
	@echo "$(BLUE)Fetching logs for service $(service)...$(RESET)"
	$(KUBECTL) logs -f deployment/$(service) -n $(NAMESPACE)

k8s-port-forward:
	@echo "$(BLUE)Setting up port forwarding for Istio Gateway...$(RESET)"
	kubectl port-forward -n istio-system svc/istio-ingress 8080:80

# Development Commands
.PHONY: dev prod clean

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

# Déploiement des applications
deploy-apps:
	$(KUBECTL) apply -f kubernetes/database-deployment.yaml --validate=false
	$(call SLEEP,5)
	$(KUBECTL) apply -f kubernetes/authentification-deployment.yaml --validate=false
	$(KUBECTL) apply -f kubernetes/booking-deployment.yaml --validate=false

# Configuration commune pour dev et prod
deploy-env:
	$(MAKE) check-cluster && \
	$(KUBECTL) config set-context --current --namespace=$(NAMESPACE) && \
	$(MAKE) create-namespace && \
	$(MAKE) setup-istio && \
	$(MAKE) deploy-apps

# Développement
dev:
	$(call SET_NAMESPACE,development) $(MAKE) deploy-env

# Production
prod:
	$(call SET_NAMESPACE,production) $(MAKE) deploy-env

# Nettoyage
clean:
	@echo "$(RED)Cleaning up resources...$(RESET)"
	$(MAKE) clean-istio
	-$(KUBECTL) delete namespace development --timeout=60s --ignore-not-found
	-$(KUBECTL) delete namespace production --timeout=60s --ignore-not-found
	@echo "$(GREEN)Cleanup completed$(RESET)"

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
	@echo "  k8s-port-forward- Forward Istio Gateway port"
	@echo "\n$(GREEN)Environment commands:$(RESET)"
	@echo "  dev            - Deploy to development"
	@echo "  prod           - Deploy to production"
	@echo "  clean          - Clean up all resources"
	@echo "\nUse PROFILE=<profile> to specify Docker Compose profile"
	@echo "Use NAMESPACE=<namespace> to specify Kubernetes namespace"

# Default target
.DEFAULT_GOAL := help
