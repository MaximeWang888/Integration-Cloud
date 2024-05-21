package org.efrei;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @GetMapping("/ping")
    public String ping() {
        return "Booking service is running correctly !";
    }
}