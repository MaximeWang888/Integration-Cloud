# Cleaning 
down:
	docker compose --profile $(profile) down

# Building
build:
	docker compose --profile $(profile) build

# Up
up:
	docker compose --profile $(profile) up -d