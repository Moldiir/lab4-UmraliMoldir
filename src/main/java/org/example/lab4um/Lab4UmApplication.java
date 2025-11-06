package org.example.lab4um;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.example.lab4um")
@EntityScan("org.example.lab4um.entity")
@EnableJpaRepositories("org.example.lab4um.repository")

public class Lab4UmApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab4UmApplication.class, args);
    }

}
