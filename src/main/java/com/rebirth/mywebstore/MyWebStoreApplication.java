package com.rebirth.mywebstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MyWebStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWebStoreApplication.class, args);
    }

}
