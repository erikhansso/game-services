#!/bin/bash

# Get the directory of the script
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Navigate to project root (assuming scripts/ is inside the root)
PROJECT_ROOT="$SCRIPT_DIR/.."

echo "Recreating services..."
kubectl apply -f "$PROJECT_ROOT/game-session/k8s/"
kubectl apply -f "$PROJECT_ROOT/matchmaking/k8s/"
kubectl apply -f "$PROJECT_ROOT/gateway/k8s/"

echo "Scaling up all deployments..."
kubectl scale deployment game-session-deployment --replicas=2
kubectl scale deployment matchmaking-deployment --replicas=2
kubectl scale deployment gateway-deployment --replicas=2

echo "All services scaled up."
kubectl get pods
