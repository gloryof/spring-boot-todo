FROM java:8

ARG appPort=8080
RUN apt-get update
RUN apt-get install -y maven

RUN git clone -q https://github.com/gloryof/spring-boot-todo

WORKDIR /spring-boot-todo/springboot-todo
RUN mvn clean package

WORKDIR /spring-boot-todo/springboot-todo/target
RUN cp springboot-todo-*.jar /springboot-todo.jar

WORKDIR /spring-boot-todo/env/conf/develop/app
RUN cp conf.yml /conf.yml

EXPOSE ${appPort}

WORKDIR /

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java", "-jar", "springboot-todo.jar", "--spring.config.location=/conf.yml", "--server.port=${APP_PORT}", "--spring.redis.host=${REDIS_HOST}",  "--spring.redis.port=${REDIS_PORT}" ]
