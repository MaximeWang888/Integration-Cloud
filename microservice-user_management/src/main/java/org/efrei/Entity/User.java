package org.efrei.Entity;

public class User {

    private String id;
    private String name;
    private String email;
    private String permission;

    // Constructeur par défaut
    public User() {
    }

    // Constructeur avec paramètres
    public User(String id, String name, String email, String permission) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.permission = permission;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    // Méthode toString() pour faciliter le débogage
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
