package org.efrei;

import org.efrei.Entity.User;
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

    @PostMapping("/logout")
    public ResponseEntity<User> logout(@RequestParam Long userId) {
        return ResponseEntity.ok(authService.logout(userId));
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
