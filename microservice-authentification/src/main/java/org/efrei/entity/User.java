package org.efrei.entity;

import javax.persistence.*;

@Entity
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

