#!/bin/bash
# 打jar包
./gradlew buildTodoCoderJar
# 构建docker镜像
docker build -t springboot/springboot-5619:v1.0.0 .
# 运行镜像
docker run --name=springboot-5619 -d -p 8082:8080 springboot/springboot-5619:v1.0.0

