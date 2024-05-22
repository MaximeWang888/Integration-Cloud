package org.efrei.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserDetail {
    @Id
    private Long id;
    private String email;
    private String phone;
    private String address;

    public void setId(Long id) {
        this.id = id;
    }

    // getters and setters
}
