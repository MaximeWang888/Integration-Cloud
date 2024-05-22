package org.efrei;

import org.efrei.entity.Tracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tracking")
public class TrackingController {
    @Autowired
    private TrackingService trackingService;

    @GetMapping("/{bookingId}")
    public ResponseEntity<List<Tracking>> getTrackingByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(trackingService.getTrackingByBookingId(bookingId));
    }

    @GetMapping("/ping")
    public String ping() {
        return "Tracking service is running correctly !";
    }
}

