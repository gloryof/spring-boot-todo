FROM java:8

COPY target/springboot-todo.jar springboot-todo.jar
COPY target/conf.yml conf.yml

CMD ["java", "-jar", "springboot-todo.jar", "--spring.config.location=/conf.yml", "--server.port=${APP_PORT}", "--spring.redis.host=${REDIS_HOST}",  "--spring.redis.port=${REDIS_PORT}" ]
