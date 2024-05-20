package org.efrei;

import org.efrei.Entity.Listing;
import org.efrei.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListingController {
    @Autowired
    private ListingService listingService;

    @GetMapping("/search/listings")
    public List<Listing> searchListings(@RequestParam String location, @RequestParam String type, @RequestParam int budget) {
        return listingService.searchListings(location, type, budget);
    }

    @GetMapping("/search/listings/{listingId}")
    public Listing getListingDetails(@RequestParam String listingId) {
        return listingService.getListingDetails(listingId);
    }

    @GetMapping("/search/filter")
    public List<Listing> filterListings(@RequestParam String type, @RequestParam String location, @RequestParam int minPrice) {
        return listingService.filterListings(type, location, minPrice);
    }
}