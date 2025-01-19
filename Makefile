# Cleaning 
down:
	docker compose --profile $(profile) down

# Building
build:
	docker compose --profile $(profile) build

# Up
up:
	docker compose --profile $(profile) up -d


# Apply the Kubernetes Deployment files on infra services
up-infra-k8s:
	kubectl apply -f k8s-manifests/eureka-deployment.yaml
	kubectl apply -f k8s-manifests/database-deployment.yaml
	kubectl apply -f k8s-manifests/metrics-server-deployment.yaml

# Apply the Kubernetes Deployment files for each microservices
up-microservices-k8s:
	kubectl apply -f k8s-manifests/authentification-deployment.yaml
	kubectl apply -f k8s-manifests/booking-deployment.yaml
	kubectl apply -f k8s-manifests/listing-deployment.yaml
	kubectl apply -f k8s-manifests/tracking-deployment.yaml
	kubectl apply -f k8s-manifests/user_management-deployment.yaml


# Delete the Kubernetes resources (Infra services)
down-infra-k8s:
	kubectl delete -f k8s-manifests/eureka-deployment.yaml
	kubectl delete -f k8s-manifests/database-deployment.yaml
	kubectl delete -f k8s-manifests/metrics-server-deployment.yaml

# Delete the Kubernetes resources (microservices)
down-microservices-k8s:
	kubectl delete -f k8s-manifests/authentification-deployment.yaml
	kubectl delete -f k8s-manifests/booking-deployment.yaml
	kubectl delete -f k8s-manifests/listing-deployment.yaml
	kubectl delete -f k8s-manifests/tracking-deployment.yaml
	kubectl delete -f k8s-manifests/user_management-deployment.yaml


# Apply Individually Each Kubernetes Deployment files
up-eureka:
	kubectl apply -f k8s-manifests/eureka-deployment.yaml

up-database:
	kubectl apply -f k8s-manifests/database-deployment.yaml

up-authentification:
	kubectl apply -f k8s-manifests/authentification-deployment.yaml

up-booking:
	kubectl apply -f k8s-manifests/booking-deployment.yaml

up-listing:
	kubectl apply -f k8s-manifests/listing-deployment.yaml

up-tracking:
	kubectl apply -f k8s-manifests/tracking-deployment.yaml

up-user-management:
	kubectl apply -f k8s-manifests/user_management-deployment.yaml


# Delete Individually Each Kubernetes Deployment files
down-eureka:
	kubectl delete -f k8s-manifests/eureka-deployment.yaml

down-database:
	kubectl delete -f k8s-manifests/database-deployment.yaml

down-authentification:
	kubectl delete -f k8s-manifests/authentification-deployment.yaml

down-booking:
	kubectl delete -f k8s-manifests/booking-deployment.yaml

down-listing:
	kubectl delete -f k8s-manifests/listing-deployment.yaml

down-tracking:
	kubectl delete -f k8s-manifests/tracking-deployment.yaml

down-user-management:
	kubectl delete -f k8s-manifests/user_management-deployment.yaml


# Kubernetes Port Forwarding for all services in one command (background execution)
up-port-forward-all:
	@echo "Starting port-forwarding for all services..."
	@start /B kubectl port-forward svc/eureka 8761:8761 > .logs/eureka-port-forward.log 2>&1
	@start /B kubectl port-forward svc/database 3306:3306 > .logs/database-port-forward.log 2>&1
	@start /B kubectl port-forward svc/authentification 8081:8081 > .logs/authentification-port-forward.log 2>&1
	@start /B kubectl port-forward svc/booking 8082:8082 > .logs/booking-port-forward.log 2>&1
	@start /B kubectl port-forward svc/listing 8083:8083 > .logs/listing-port-forward.log 2>&1
	@start /B kubectl port-forward svc/tracking 8084:8084 > .logs/tracking-port-forward.log 2>&1
	@start /B kubectl port-forward svc/user-management 8085:8085 > .logs/user-management-port-forward.log 2>&1

# Stop all kubectl port-forward processes (Windows)
down-port-forward-all:
	@echo "Stopping all kubectl port-forward processes..."
	@taskkill /F /IM kubectl.exe


### Kubernetes Port Forwarding for each service

# Port forward for Eureka service
port-forward-eureka:
	kubectl port-forward svc/eureka 8761:8761

# Port forward for Database service (MySQL)
port-forward-database:
	kubectl port-forward svc/database 3306:3306

# Port forward for Authentification service
port-forward-authentification:
	kubectl port-forward svc/authentification 8081:8081

# Port forward for Booking service
port-forward-booking:
	kubectl port-forward svc/booking 8082:8082

# Port forward for Listing service
port-forward-listing:
	kubectl port-forward svc/listing 8083:8083

# Port forward for Tracking service
port-forward-tracking:
	kubectl port-forward svc/tracking 8084:8084

# Port forward for User Management service
port-forward-user-management:
	kubectl port-forward svc/user-management 8085:8085


### SIMULATE to see if HPA is working or not

# stress:
# 	@echo Running both CPU and memory stress on the authentification service...
# 	@for /f "delims=" %%b in ('kubectl get pods -l app=authentification -o jsonpath="{.items[0].metadata.name}"') do (
# 		echo Simulating CPU load on service authentification (pod %%b)
# 		kubectl exec -it %%b -- dd if=/dev/zero of=/dev/null bs=1M count=1000
# 		echo Simulating Memory load on service authentification (pod %%b)
# 		kubectl exec -it %%b -- dd if=/dev/zero of=/dev/shm/bigfile bs=1M count=1024
# 	)
