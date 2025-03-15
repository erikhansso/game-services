Running the microservices locally

1. Build all microservices by running ./gradlew build in the root of the project.
2. Initialize the db. Easeiest way is to run the db image via the docker-compose file (by running `docker compose up db` in the root of the project). Otherwise you need to start your postgres db server and execute the ./scripts/init-dbs.sh script against it, so that each microservices database is initialized before the actual services start.
3. Start the service.

Running the microservices in a local K8s cluster

In the ./scripts folder is a script 'k8s-local-deploy.sh' for running the microservices
in a local Kubernetes cluster.
It deletes existing deployments, builds docker images for all the microservices,
applies their k8s manifests with kubectl and waits for the readiness probe to complete.
