#!/bin/bash
# 打jar包
./gradlew buildTodoCoderJar
# 构建docker镜像
docker build -t springboot/springboot-5619:v1.0.0 ./docker/springboot
# 运行镜像
docker run --name=springboot-5619 -d --restart=always --add-host host.docker.internal:host-gateway -p 8082:8080 springboot/springboot-5619:v1.0.0
# Run the sql file
docker build -t liquibase/liquibase-mysql   ./docker/liquibase
docker run --rm -it -v "$(pwd)/src/main/resources/db/changelog:/liquibase/changelog" liquibase/liquibase-mysql  update --defaultsFile=/liquibase/changelog/liquibase.docker.properties

