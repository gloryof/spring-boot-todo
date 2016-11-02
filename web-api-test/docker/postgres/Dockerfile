FROM postgres:latest

RUN apt-get update &&  apt-get install -y git wget openjdk-7-jdk && 


RUN wget https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/4.0.3/flyway-commandline-4.0.3-linux-x64.tar.gz -q && \
    tar -xvf flyway-commandline-4.0.3-linux-x64.tar.gz

ENV PATH /flyway-4.0.3:$PATH

COPY create-init-data.sh /usr/local/bin/create-init-data.sh
RUN chmod a+x /usr/local/bin/create-init-data.sh

WORKDIR /var/lib/spring-todo/git
RUN git clone -q https://github.com/gloryof/spring-boot-todo && \
    cp /spring-boot-todo/env/init/db/create-database.sh /docker-entrypoint-initdb.d/create-database.sh

CMD ["/usr/local/bin/create-init-data.sh"]