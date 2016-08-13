FROM postgres:latest

RUN apt-get update
RUN apt-get install -y git
RUN git clone -q https://github.com/gloryof/spring-boot-todo
RUN cp /spring-boot-todo/env/init/db/create-database.sh /docker-entrypoint-initdb.d/create-database.sh
