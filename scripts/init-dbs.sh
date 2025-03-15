#!/bin/bash

set -e
set -u

function create_database() {
  local database=$1
  echo "Creating database '$database'"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE $database;
    GRANT ALL PRIVILEGES ON DATABASE $database TO $POSTGRES_USER;
EOSQL
}

if [ -n "$ALL_DBS" ]; then
  echo "Creating microservices databases: $ALL_DBS"
  for db in $(echo $ALL_DBS | tr ',' ' '); do
    create_database $db
  done
  echo "All microservices databases created"
fi 