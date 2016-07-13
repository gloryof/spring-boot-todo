#!/bin/sh

docker run -e TZ=Asia/Tokyo --name db01 -d -p 5432:5432 -it spring-todo:db01
