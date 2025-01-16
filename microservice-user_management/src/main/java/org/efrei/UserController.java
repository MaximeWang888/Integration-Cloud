package org.efrei;

import org.efrei.clients.AuthServiceClient;
import org.efrei.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthServiceClient authClient;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long userId) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(userService.getUserDetail(userId));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateUserDetail(@PathVariable Long userId, @RequestBody UserDetail userDetail) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(userService.updateUserDetail(userId, userDetail));
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<?> removeUser(@PathVariable Long userId) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkConnection/{userId}")
    public <userId> Boolean isUserLoggedIn(@PathVariable Long userId) {
        return authClient.isUserLoggedIn(userId);
    }

    @GetMapping("/pingAuthentification")
    public String pingAuthentification() {
        return userService.ping();
    }

    @GetMapping("/ping")
    public String ping() {
        return "User Management service is running correctly !";
    }
}
