#!/bin/sh

docker run \
       -e TZ=Asia/Tokyo \
       -e TARGET_IP=192.168.99.102 \
       -e TARGET_PORT=5432 \
       -e TARGET_DB=boot-todo \
       -e DB_USER=todo-user \
       -e DB_PASSWORD=todo_user \
       -e DDL_PATH=/var/lib/spring-todo/git/spring-boot-todo/env/init/db/ddl \
       --rm \
       -v /var/lib/spring-todo/git:/var/lib/spring-todo/git \
       --name flayway \
       -it spring-todo:flyway
