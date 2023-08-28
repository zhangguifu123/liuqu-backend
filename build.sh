#!/bin/bash
# 打jar包
./gradlew buildTodoCoderJar
# 构建docker镜像
docker build -t todocoder/todocoder-gradle:v1.0.0 .
# 运行镜像
docker run --name=todocoder-gradle -d -p 8080:8080 todocoder/todocoder-gradle:v1.0.0

