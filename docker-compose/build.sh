# !/bin/bash

cp -f ../env/conf/develop/conf.yml  app/target/conf.yml
cp -f ../env/init/db/create-database.sh postgres/copies/

docker-compose up -d
