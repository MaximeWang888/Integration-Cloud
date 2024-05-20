package org.efrei.DAO;

import org.efrei.Entity.Listing;
import java.util.List;

public interface IListingDAO {

    List<Listing> findByLocationAndTypeAndBudget(String location, String type, int budget);

    Listing findById(String listingId);

    List<Listing> findByTypeAndLocationAndMinPrice(String type, String location, int minPrice);

    Listing save(Listing listing);

    void update(Listing listing);

    void delete(String listingId);
}
