package com.shixingxu.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 拾星序官网后端 API 服务启动入口。
 */
@SpringBootApplication
@MapperScan("com.shixingxu.portal.mapper")
public class ShixingxuPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShixingxuPortalApplication.class, args);
    }
}
