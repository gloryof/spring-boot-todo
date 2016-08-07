#!/bin/sh

set -e

INIT_FLG_FILE=${PGDATA}/execute-init

if [ ! -e ${INIT_FLG_FILE} ]; then

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER "todo-user"
      WITH PASSWORD 'boot-todo';
    CREATE DATABASE "boot-todo"
      WITH OWNER = "todo-user"
        ENCODING = "UTF-8";
    GRANT ALL PRIVILEGES ON DATABASE "boot-todo" TO "todo-user";
EOSQL
else
echo 'init file is exists.'
fi

touch ${INIT_FLG_FILE}
