#!/bin/sh

cd /spring-boot-todo
git pull

MIGRATE_URL=jdbc:postgresql://${TARGET_IP}:${TARGET_PORT}/boot-todo
DB_USER=todo-user
DB_PASSWORD=todo_user
DDL_PATH=/spring-boot-todo/env/init/db/ddl

flyway -url=${MIGRATE_URL} -user=${DB_USER} -password=${DB_PASSWORD} -locations=filesystem:${DDL_PATH} migrate
