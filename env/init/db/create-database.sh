#!/bin/sh

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER "todo-user";
    CREATE DATABASE "boot-todo";
    GRANT ALL PRIVILEGES ON DATABASE "boot-todo" TO "todo-user";
EOSQL
