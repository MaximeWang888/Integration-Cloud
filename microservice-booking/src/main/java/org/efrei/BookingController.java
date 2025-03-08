package org.efrei;

import org.efrei.clients.AuthServiceClient;
import org.efrei.entity.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/bookings")
@FeignClient(name = "authentification")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private AuthServiceClient authClient;
    @Value("${authentificationURL}")
    private String authentificationURL;
    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/new")
    public ResponseEntity<?> createBooking(@RequestParam Long userId, @RequestBody Booking booking) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(@RequestParam Long userId, @PathVariable Long bookingId) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    @PutMapping("/{bookingId}/update")
    public ResponseEntity<?> updateBooking(@RequestParam Long userId, @PathVariable Long bookingId, @RequestBody Booking booking) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, booking));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@RequestParam Long userId, @PathVariable Long bookingId) {
        if (!isUserLoggedIn(userId)) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkConnection/{userId}")
    public Boolean isUserLoggedIn(@PathVariable Long userId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String finalURL = authentificationURL + "/api/auth/checkConnection/" + userId;
            logger.info("URL: " + finalURL);
            Boolean result = restTemplate.getForObject(finalURL, Boolean.class);
            logger.info(finalURL + " " + result);
            return result;
        } catch (Exception e) {
            logger.info("Unexpected error while checking user connection for user");
            return false;
        }
    }
    
    @GetMapping("/ping")
    public String ping() {
        return "Booking service is running correctly!";
    }

    @GetMapping("/whoami")
    public String whoami() {
        return "Team name: Les Ninjas !";
    }
}
