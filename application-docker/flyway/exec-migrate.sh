#!/bin/sh
cd /var/lib/spring-todo/git/

MIGRATE_URL=jdbc:postgresql://${TARGET_IP}:${TARGET_PORT}/${TARGET_DB}

until psql -h "${TARGET_IP}" -d "${TARGET_DB}" -U "${DB_USER}" -c '\l'; do
    >&2 echo "Postgres is unavailable - sleeping"
    sleep 1
done

flyway -url=${MIGRATE_URL} -user=${DB_USER} -password=${DB_PASSWORD} -locations=filesystem:${DDL_PATH} migrate
