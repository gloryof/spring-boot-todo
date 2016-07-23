#!/bin/sh

docker run -e TZ=Asia/Tokyo -e APP_PORT=8080 -e REDIS_HOST=192.168.99.103 -e REDIS_PORT=6379 --name ap01 -d -p 8080:8080 -it spring-todo:ap01
