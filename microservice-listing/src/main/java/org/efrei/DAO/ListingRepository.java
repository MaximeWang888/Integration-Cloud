package org.efrei.DAO;

import org.efrei.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByAvailability(Boolean availability);
}
