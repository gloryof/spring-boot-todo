#!/bin/sh

docker build --build-arg tz=Asia/Tokyo  -t spring-todo:web01 .
