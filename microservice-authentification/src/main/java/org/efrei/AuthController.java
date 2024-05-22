package org.efrei;

import org.efrei.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        return ResponseEntity.ok(authService.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }

    @GetMapping("/ping")
    public String ping() {
        return "Authentication service is running correctly!";
    }

    @GetMapping("/ping/{token}")
    public String ping(@PathVariable String token) {
        return "Authentication service is running correctly! Token: " + token;
    }
}
