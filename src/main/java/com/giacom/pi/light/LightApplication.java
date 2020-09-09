package com.giacom.pi.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LightApplication {

    public static void main(String[] args) {
        System.setProperty("user.timezone", "America/Sao_Paulo");
        SpringApplication.run(LightApplication.class, args);
    }

}
