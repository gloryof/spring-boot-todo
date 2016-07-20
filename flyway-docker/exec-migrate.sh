#!/bin/sh

if [ ! -e /var/lib/spring-todo/git/spring-boot-todo ]; then
    cd /var/lib/spring-todo/git/
    git clone -q https://github.com/gloryof/spring-boot-todo
else
    cd /var/lib/spring-todo/git/spring-boot-todo
    git pull
fi

MIGRATE_URL=jdbc:postgresql://${TARGET_IP}:${TARGET_PORT}/${TARGET_DB}

flyway -url=${MIGRATE_URL} -user=${DB_USER} -password=${DB_PASSWORD} -locations=filesystem:${DDL_PATH} migrate
