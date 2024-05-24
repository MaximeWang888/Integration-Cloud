package org.efrei;

import org.efrei.DAO.UserRepository;
import org.efrei.Entity.Role;
import org.efrei.Entity.User;
import org.efrei.entity.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private UserRepository userRepository;

    private boolean isUserLoggedIn(Long userId) {
        return userRepository.findById(userId).get().getIsConnected();
    }

    private boolean isProprietaire(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null && user.getRole() == Role.PROPRIETAIRE;
    }

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

    @GetMapping("/ping")
    public String ping() {
        return "Listing service is running correctly!";
    }
}
