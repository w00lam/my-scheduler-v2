package com.woolam.myschedulerv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
public class MySchedulerV2Application {

    public static void main(String[] args) {
        SpringApplication.run(MySchedulerV2Application.class, args);
    }

}
