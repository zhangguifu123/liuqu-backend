# 留趣APP

### 环境配置

- Java 17
- Docker

### 项目部署

- 配置Java 17 + gradle，可运行build.gradle文件
- 运行`./build.sh`

##### build.sh介绍

部署脚本，它包括了构建 Java 应用程序的 JAR 文件、构建 Docker 镜像、运行 Docker 容器以及运行 Liquibase 数据库迁移的步骤。这里是脚本的每个部分的解释：

1. `./gradlew buildTodoCoderJar`: 使用 Gradle 构建工具运行一个自定义任务 `buildTodoCoderJar`，这个任务可能会编译 Java 代码，运行测试，并且将生成的 JAR 文件拷贝到指定位置。

2. `docker build -t springboot/springboot-liuqu:v1.0.0 -f ./docker/springboot/Dockerfile .`: 构建一个 Docker 镜像，标签为 `springboot/springboot-liuqu:v1.0.0`，使用位于 `./docker/springboot/Dockerfile` 路径下的 Dockerfile。

3. `docker run --name=springboot-liuqu -d --restart=always --add-host host.docker.internal:host-gateway -p 8083:8080 springboot/springboot-liuqu:v1.0.0`: 运行一个 Docker 容器，容器名为 `springboot-liuqu`，以守护进程模式运行，并设置为始终重启。将容器的 8080 端口映射到宿主机的 8083 端口，并添加 `host.docker.internal` 到宿主的网关。

4. `docker build -t liquibase/liquibase-mysql ./docker/liquibase`: 构建 Liquibase 用于 MySQL 的 Docker 镜像。

5. `docker run --rm -it -v "$(pwd)/src/main/resources/db/changelog:/liquibase/changelog" liquibase/liquibase-mysql update --defaultsFile=/liquibase/changelog/liquibase.docker.properties`: 运行一个 Docker 容器，执行 Liquibase 更新操作。使用当前目录下的 `src/main/resources/db/changelog` 目录作为映射到容器内的卷，并指定 Liquibase 配置文件。

确保您执行这个脚本时，当前目录是项目的根目录，所有路径和文件都存在并可访问。在运行脚本之前，您可能需要给它执行权限，可以使用 `chmod +x your-script.sh` 命令来实现。

如果脚本按预期工作，它将自动完成应用程序的构建和部署。如果您在执行过程中遇到问题，请确保 Docker 已正确安装，并且您有足够的权限来执行所有列出的命令。
