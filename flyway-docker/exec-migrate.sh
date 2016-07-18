#!/bin/sh

cd /spring-boot-todo
git pull

MIGRATE_URL=jdbc:postgresql://${TARGET_IP}:${TARGET_PORT}/${TARGET_DB}

flyway -url=${MIGRATE_URL} -user=${DB_USER} -password=${DB_PASSWORD} -locations=filesystem:${DDL_PATH} migrate
