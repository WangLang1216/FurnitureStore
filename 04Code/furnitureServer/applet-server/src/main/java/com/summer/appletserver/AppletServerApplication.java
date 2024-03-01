package com.summer.appletserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.summer.securitymodule", "com.summer.commonmodule", "com.summer.appletserver"})
public class AppletServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppletServerApplication.class, args);
        System.out.println("小程序服务 启动成功！");
    }

}
