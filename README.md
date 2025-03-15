In the /scripts folder is a script 'k8s-local-deploy.sh' for running the microservices 
in a local Kubernetes cluster. 
It deletes existing deployments, builds docker images for all the microservices, 
applies their k8s manifests with kubectl and does a liveness probe by waiting for all pods to be in 'Running' state.