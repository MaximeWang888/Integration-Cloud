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
.PHONY: dev prod clean

dev: export NAMESPACE=development
dev: k8s-apply-all ingress-enable ingress-apply

prod: export NAMESPACE=production
prod: k8s-apply-all ingress-enable ingress-apply

clean: ingress-delete
	@echo "$(RED)Cleaning up all resources...$(RESET)"
	-$(MAKE) dc-down
	-$(MAKE) k8s-delete-all NAMESPACE=development
	-$(MAKE) k8s-delete-all NAMESPACE=production
	@echo "$(GREEN)Cleanup completed!$(RESET)"

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
