FROM postgres:latest

RUN apt-get update
RUN apt-get install -y wget git
RUN apt-get install -y openjdk-7-jdk

RUN wget https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/4.0.3/flyway-commandline-4.0.3-linux-x64.tar.gz -q
RUN tar -xvf flyway-commandline-4.0.3-linux-x64.tar.gz

ENV PATH /flyway-4.0.3:$PATH

COPY exec-migrate.sh /usr/local/bin/exec-migrate.sh
RUN chmod a+x /usr/local/bin/exec-migrate.sh

RUN git clone -q https://github.com/gloryof/spring-boot-todo

VOLUME /var/lib/spring-todo/git

CMD ["/usr/local/bin/exec-migrate.sh"]
