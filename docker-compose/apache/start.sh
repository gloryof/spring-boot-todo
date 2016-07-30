#!/bin/sh

docker run -e TZ=Asia/Tokyo -e LANG=ja_JP.UTF-8 --name web01 -d -p 80:80 -it spring-todo:web01
