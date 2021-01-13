package org.example.tatrainingapi;

import org.example.tatrainingapi.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityConfig.class})
public class TaTrainingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaTrainingApiApplication.class, args);
    }

}
