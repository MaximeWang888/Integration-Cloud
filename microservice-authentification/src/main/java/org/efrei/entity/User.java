package org.efrei.Entity;

import org.efrei.Entity.Role;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    // getters and setters

    public String getPassword() {
        return password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

