#!/bin/sh

docker run -e LANG=ja_JP.UTF-8 --name web01 -d -p 80:80 -it apache:web01
