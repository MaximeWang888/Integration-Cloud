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

    public Listing getListing(Integer listingId) {
        return listingRepository.findById(listingId).orElseThrow(() -> new RuntimeException("Listing not found"));
    }
    public Listing getListingByTitle(String title) {
        return listingRepository.findByTitle(title);
    }
    public Listing updateListing(Integer listingId, Listing listing) {
        if (!listingRepository.existsById(listingId)) {
            throw new RuntimeException("Listing not found");
        }
        listing.setId(listingId);
        return listingRepository.save(listing);
    }

    public void removeListing(Integer listingId) {
        listingRepository.deleteById(listingId);
    }
}
