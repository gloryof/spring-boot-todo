#!/bin/sh

docker run --privileged --name web01 -d -p 80:80 -v /var/log/docker/httpd:/var/log/httpd -it apache:web01
