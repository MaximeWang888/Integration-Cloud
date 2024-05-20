package org.efrei.Entity;

public class Listing {

    private String id;
    private String location;
    private String type;
    private int price;

    // Constructeur
    public Listing(String id, String location, String type, int price) {
        this.id = id;
        this.location = location;
        this.type = type;
        this.price = price;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Autres méthodes si nécessaire
}
