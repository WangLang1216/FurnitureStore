FROM dockette/jdk8:latest
# 新建目录
RUN mkdir -p /workdir/applet
# 设置容器目录
WORKDIR /workdir/applet
# 复制jar包和文件
COPY applet-server-1.0-SNAPSHOT.jar /workdir/applet/applet-server-1.0-SNAPSHOT.jar
COPY application.yml /workdir/applet/application.yml
# 开放端口
EXPOSE 8081
# 启动
ENTRYPOINT ["java", "-jar", "/workdir/applet/applet-server-1.0-SNAPSHOT.jar"]
# 引用外部配置文件
CMD ["--spring.config.additional-location=/workdir/applet/application.yml"]