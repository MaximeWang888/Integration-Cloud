package org.efrei.DAO;

import org.efrei.Entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO implements IUserDAO {

    public User findById(String userId) {
        return null;
        // Implémentation de l'accès aux données pour récupérer un utilisateur par son ID
    }

    public void updatePermissions(String userId, String permission) {
        // Implémentation de l'accès aux données pour mettre à jour les permissions d'un utilisateur
    }
}