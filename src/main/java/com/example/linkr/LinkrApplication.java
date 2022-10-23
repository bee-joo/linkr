package com.example.linkr;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LinkrApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkrApplication.class, args);
    }

    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator();
    }
}
