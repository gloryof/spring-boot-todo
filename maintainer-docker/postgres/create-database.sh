#!/bin/sh

set -e

INIT_FLG_FILE=${PGDATA}/execute-init

if [ ! -e ${INIT_FLG_FILE} ]; then

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER "zabbix-user"
      WITH PASSWORD 'zabbix-password';
    CREATE DATABASE "zabbix"
      WITH OWNER = "zabbix-user"
        ENCODING = "UTF-8";
    GRANT ALL PRIVILEGES ON DATABASE "zabbix" TO "zabbix-user"
EOSQL
else
echo 'init file is exists.'
fi

touch ${INIT_FLG_FILE}
