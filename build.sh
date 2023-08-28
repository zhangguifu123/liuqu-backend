#!/bin/bash
# 打jar包
./gradlew buildTodoCoderJar
# 构建docker镜像
docker build -t springboot/springboot-liuqu:v1.0.0 .
# 运行镜像
docker run --name=springboot-liuqu-d -p 8080:8080 springboot/springboot-liuqu:v1.0.0

