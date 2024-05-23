package org.efrei;

import org.efrei.DAO.UserRepository;
import org.efrei.Entity.Role;
import org.efrei.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User signup(User user) {
        user.setRole(Role.LOCATAIRE);
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setIsConnected(true);
            userRepository.save(user);
            return user;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public User logout(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsConnected(false);
            userRepository.save(user);
            return user;
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
