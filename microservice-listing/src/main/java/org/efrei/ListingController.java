package org.efrei;

import org.efrei.clients.AuthServiceClient;
import org.efrei.entity.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private AuthServiceClient authClient;

    @PostMapping("/new")
    public ResponseEntity<?> createListing(@RequestBody Listing listing) {

        System.out.println(listing);
        return ResponseEntity.ok(listingService.createListing(listing));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getListingByTitle(@RequestParam Long userId, @PathVariable String title) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(listingService.getListingByTitle(title));
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<?> getListing(@RequestParam Long userId, @PathVariable Integer listingId) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(listingService.getListing(listingId));
    }

    @PutMapping("/{listingId}/update")
    public ResponseEntity<?> updateListing(@RequestParam Long userId, @PathVariable Integer listingId, @RequestBody Listing listing) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(listingService.updateListing(listingId, listing));
    }

    @DeleteMapping("/{listingId}/remove")
    public ResponseEntity<?> removeListing(@RequestParam Long userId, @PathVariable Integer listingId) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        listingService.removeListing(listingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkConnection/{userId}")
    public <userId> Boolean isUserLoggedIn(@PathVariable Long userId) {
        return authClient.isUserLoggedIn(userId);
    }

    @GetMapping("/ping")
    public String ping() {
        return "Listing service is running correctly!";
    }

    @GetMapping("/whoami")
    public String whoami() {
        return "Team name: Les Ninjas !";
    }
}
