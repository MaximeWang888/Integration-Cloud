package org.efrei.DAO;

import org.efrei.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Integer> {
    List<Listing> findByAvailability(Boolean availability);
    Listing findByTitle(String title);

}
