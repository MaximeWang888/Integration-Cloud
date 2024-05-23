package org.efrei;

import org.efrei.entity.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listings")
public class ListingController {
    @Autowired
    private ListingService listingService;

    @PostMapping("/new")
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.createListing(listing));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Listing> getListingByTitle(@PathVariable String title) {
        return ResponseEntity.ok(listingService.getListingByTitle(title));
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<Listing> getListing(@PathVariable Integer listingId) {
        return ResponseEntity.ok(listingService.getListing(listingId));
    }

    @PutMapping("/{listingId}/update")
    public ResponseEntity<Listing> updateListing(@PathVariable Integer listingId, @RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.updateListing(listingId, listing));
    }

    @DeleteMapping("/{listingId}/remove")
    public ResponseEntity<Void> removeListing(@PathVariable Integer listingId) {
        listingService.removeListing(listingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ping")
    public String ping() {
        return "Listing service is running correctly !";
    }
}
