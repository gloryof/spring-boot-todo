#!/bin/sh

docker run -e TZ=Asia/Tokyo --name db01 -v /var/lib/sprint-todo/postgres/data:/var/lib/postgresql/data -d -p 5432:5432 -it spring-todo:db01
