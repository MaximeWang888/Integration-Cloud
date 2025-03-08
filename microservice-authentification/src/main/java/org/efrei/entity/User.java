package org.efrei.entity;

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

    @Column(name = "is_connected")
    private Boolean isConnected;

    public Boolean getIsConnected() {
        return isConnected;
    }
    public void setIsConnected(Boolean connected) {
        isConnected = connected;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public Long getId() {
        return id;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

}

