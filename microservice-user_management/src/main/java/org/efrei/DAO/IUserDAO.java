package org.efrei.DAO;

import org.efrei.Entity.User;

public interface IUserDAO {

    User findById(String userId);

    void updatePermissions(String userId, String permission);
}
