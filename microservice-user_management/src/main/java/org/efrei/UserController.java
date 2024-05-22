package org.efrei;

import org.efrei.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetail> getUserDetail(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserDetail(userId));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<UserDetail> updateUserDetail(@PathVariable Long userId, @RequestBody UserDetail userDetail) {
        return ResponseEntity.ok(userService.updateUserDetail(userId, userDetail));
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<Void> removeUser(@PathVariable Long userId) {
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ping")
    public String ping() {
        return "User Management service is running correctly !";
    }

    @GetMapping("/pingAuthentification")
    public String pingAuthentification() {
        return userService.ping();
    }
}
