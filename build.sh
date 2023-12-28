#!/bin/bash
# build jar file
./gradlew buildTodoCoderJar

# build docker image
docker build -t springboot/springboot-liuqu:v1.0.0 -f ./docker/springboot/Dockerfile .
# Run the container by image
docker run --name=springboot-liuqu -d --restart=always --add-host host.docker.internal:host-gateway -p 8083:8080 springboot/springboot-liuqu:v1.0.0
# Run the sql file
docker build -t liquibase/liquibase-mysql   ./docker/liquibase
docker run --rm -it -v "$(pwd)/src/main/resources/db/changelog:/liquibase/changelog" liquibase/liquibase-mysql  update --defaultsFile=/liquibase/changelog/liquibase.docker.properties

