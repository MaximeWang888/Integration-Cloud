package org.efrei.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long listingId;
    private Long userId;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // getters and setters
}

