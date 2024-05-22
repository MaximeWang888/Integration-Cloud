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

    @GetMapping("/{listingId}")
    public ResponseEntity<Listing> getListing(@PathVariable Long listingId) {
        return ResponseEntity.ok(listingService.getListing(listingId));
    }

    @PutMapping("/{listingId}/update")
    public ResponseEntity<Listing> updateListing(@PathVariable Long listingId, @RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.updateListing(listingId, listing));
    }

    @DeleteMapping("/{listingId}/remove")
    public ResponseEntity<Void> removeListing(@PathVariable Long listingId) {
        listingService.removeListing(listingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ping")
    public String ping() {
        return "Listing service is running correctly !";
    }
}
