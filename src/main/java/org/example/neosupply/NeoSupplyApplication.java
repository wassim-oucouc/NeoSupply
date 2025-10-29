package org.example.neosupply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.neosupply", "org.example.neosupply.mapper"})
public class NeoSupplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeoSupplyApplication.class, args);
    }

}
