package org.efrei;

import org.efrei.DAO.UserDetailRepository;
import org.efrei.clients.AuthServiceClient;
import org.efrei.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private AuthServiceClient authServiceClient;

    public UserDetail getUserDetail(Long userId) {
        return userDetailRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetail updateUserDetail(Long userId, UserDetail userDetail) {
        if (!userDetailRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userDetail.setId(userId);
        return userDetailRepository.save(userDetail);
    }

    public void removeUser(Long userId) {
        userDetailRepository.deleteById(userId);
    }

    public String ping() {
        return authServiceClient.ping("Je viens du service UserService !");
    }
}
