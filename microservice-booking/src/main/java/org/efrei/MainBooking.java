package org.efrei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MainBooking {
    public static void main(String[] args) {
        SpringApplication.run(MainBooking.class, args);
    }

    @GetMapping("/hello1")
    public String sayHello() {
        return "Hello, World!";
    }
}