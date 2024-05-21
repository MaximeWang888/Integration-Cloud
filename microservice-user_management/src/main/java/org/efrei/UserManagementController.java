package org.efrei;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagementController {
    @GetMapping("/ping")
    public String ping() {
        return "User Management service is running correctly !";
    }
}
