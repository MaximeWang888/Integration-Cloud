package org.efrei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MainListing {
    public static void main(String[] args) {
        SpringApplication.run(MainListing.class, args);
    }

    @GetMapping("/hello2")
    public String sayHello() {
        return "Hello, World!";
    }
}