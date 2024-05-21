package org.efrei;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/ping")
    public String ping() {
        return "Authentification service is running correctly !";
    }

}