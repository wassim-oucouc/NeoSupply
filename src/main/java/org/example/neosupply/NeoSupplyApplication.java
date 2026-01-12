package org.example.neosupply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = {"org.example.neosupply", "org.example.neosupply.mapper"})
public class NeoSupplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeoSupplyApplication.class, args);
    }

}
