package org.efrei;

import org.efrei.DAO.UserRepository;
import org.efrei.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/whoami")
    public String whoami() {
        return "Team name: Les Ninjas !";
    }

    @GetMapping("/checkConnection/{userId}")
    public boolean isUserConnected(@PathVariable Long userId) {
        return userRepository.findById(userId).get().getIsConnected();
    }
}
