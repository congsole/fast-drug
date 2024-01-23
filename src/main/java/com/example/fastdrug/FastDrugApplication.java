package com.example.fastdrug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FastDrugApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastDrugApplication.class, args);
    }

}
