package com.summer.manageserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.summer.securitymodule", "com.summer.commonmodule", "com.summer.manageserver"})
public class ManageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageServerApplication.class, args);
        System.out.println("ManageServerApplication 管理平台服务启动成功");
    }

}
