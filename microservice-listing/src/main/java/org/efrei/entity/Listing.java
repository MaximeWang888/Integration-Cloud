package org.efrei.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private String type;
    private Boolean availability;

    public void setId(Long id) {
        this.id = id;
    }
// getters and setters
}

