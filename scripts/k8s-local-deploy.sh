#!/bin/bash

set -e  # Exit on first error

# Get the directory of the script
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Navigate to project root (assuming scripts/ is inside the root)
PROJECT_ROOT="$SCRIPT_DIR/.."

echo "Deleting existing deployments and services..."
kubectl delete deployment game-session-deployment --ignore-not-found=true
kubectl delete deployment matchmaking-deployment --ignore-not-found=true
kubectl delete deployment gateway-deployment --ignore-not-found=true
kubectl delete service game-session-service --ignore-not-found=true
kubectl delete service matchmaking-service --ignore-not-found=true
kubectl delete service gateway-service --ignore-not-found=true

echo "Building Docker images..."
docker build -t game-session "$PROJECT_ROOT/game-session"
docker build -t matchmaking "$PROJECT_ROOT/matchmaking"
docker build -t gateway "$PROJECT_ROOT/gateway"

echo "Applying Kubernetes manifests..."
kubectl apply -f "$PROJECT_ROOT/game-session/k8s/"
kubectl apply -f "$PROJECT_ROOT/matchmaking/k8s/"
kubectl apply -f "$PROJECT_ROOT/gateway/k8s/"

echo "Waiting for all pods to be in 'Running' state..."
kubectl wait --for=condition=ready pod --all --timeout=60s

echo "Deployment completed successfully!"
kubectl get pods
