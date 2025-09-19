package org.example.drivenow_carrental_aad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class DrivenowCarRentalAAdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivenowCarRentalAAdApplication.class, args);
    }

}
