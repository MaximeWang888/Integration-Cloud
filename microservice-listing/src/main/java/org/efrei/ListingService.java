package org.efrei;

import org.efrei.DAO.ListingRepository;
import org.efrei.entity.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public Listing getListing(Long listingId) {
        return listingRepository.findById(listingId).orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    public Listing updateListing(Long listingId, Listing listing) {
        if (!listingRepository.existsById(listingId)) {
            throw new RuntimeException("Listing not found");
        }
        listing.setId(listingId);
        return listingRepository.save(listing);
    }

    public void removeListing(Long listingId) {
        listingRepository.deleteById(listingId);
    }
}
