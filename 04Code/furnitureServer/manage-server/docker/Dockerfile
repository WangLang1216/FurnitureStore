FROM dockette/jdk8:latest
# 新建目录
RUN mkdir -p /workdir/manage
# 设置容器目录
WORKDIR /workdir/manage
# 复制jar包和文件
COPY manage-server-1.0-SNAPSHOT.jar /workdir/manage/manage-server-1.0-SNAPSHOT.jar
COPY application.yml /workdir/manage/application.yml
# 开放端口
EXPOSE 8082
# 启动
ENTRYPOINT ["java", "-jar", "/workdir/manage/manage-server-1.0-SNAPSHOT.jar"]
# 引用外部配置文件
CMD ["--spring.config.additional-location=/workdir/manage/application.yml"]