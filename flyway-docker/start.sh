#!/bin/sh

docker run \
       -e TZ=Asia/Tokyo \
       -e TARGET_IP=192.168.99.102 \
       -e TARGET_PORT=5432 \
       -e TARGET_DB=boot-todo \
       -e DB_USER=todo-user \
       -e DB_PASSWORD=todo_user \
       -e DDL_PATH=/spring-boot-todo/env/init/db/ddl \
       --rm \
       --name flayway \
       -it spring-todo:flyway
