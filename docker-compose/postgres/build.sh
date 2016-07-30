#!/bin/sh

if [ ! -e ./copies/create-database.sh ]; then
   cp ../env/init/db/create-database.sh ./copies/create-database.sh
fi

docker build -t spring-todo:db01 .
