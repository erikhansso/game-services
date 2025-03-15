#!/bin/bash

echo "Deleting services to release ports..."
kubectl delete service game-session-service
kubectl delete service matchmaking-service
kubectl delete service gateway-service

echo "Scaling down all deployments..."
kubectl scale deployment game-session-deployment --replicas=0
kubectl scale deployment matchmaking-deployment --replicas=0
kubectl scale deployment gateway-deployment --replicas=0

echo "All services scaled down."
kubectl get pods
