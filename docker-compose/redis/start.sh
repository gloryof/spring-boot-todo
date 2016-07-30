#!/bin/sh

docker run -e TZ=Asia/Tokyo -p 6379:6379 --name redis01 -d redis
