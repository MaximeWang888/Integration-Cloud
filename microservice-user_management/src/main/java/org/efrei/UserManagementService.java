package org.efrei;

import org.efrei.DAO.UserDAO;
import org.efrei.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {
    @Autowired
    private UserDAO userDAO;

    public User getUser(String userId) {
        // Récupérer l'utilisateur depuis la base de données
        return userDAO.findById(userId);
    }

    public void updateUserPermissions(String userId, String permission) {
        // Mettre à jour les permissions de l'utilisateur dans la base de données
        userDAO.updatePermissions(userId, permission);
    }
}