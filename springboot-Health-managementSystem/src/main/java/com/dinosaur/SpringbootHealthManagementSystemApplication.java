package com.dinosaur;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class SpringbootHealthManagementSystemApplication {

    public static void main(String[] args) {
        log.info("项目启动");
        SpringApplication.run(SpringbootHealthManagementSystemApplication.class, args);
    }

}
