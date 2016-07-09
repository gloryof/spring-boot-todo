#!/bin/sh

docker build --build-arg tz=Asia/Tokyo  -t apache:web01 .
