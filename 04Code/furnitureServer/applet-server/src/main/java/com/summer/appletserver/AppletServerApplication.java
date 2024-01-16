package com.summer.appletserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.summer.appletserver.mapper")
public class AppletServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppletServerApplication.class, args);
        System.out.println("小程序服务 启动成功！");
    }

}
