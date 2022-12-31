package ru.javaops.topjava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CaloriesManagementRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaloriesManagementRestApplication.class, args);
        log.info("Go to API documentation: http://localhost:8080/swagger-ui/index.html");
    }
}
