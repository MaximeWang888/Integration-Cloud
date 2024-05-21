package org.efrei;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackingController {
    @GetMapping("/ping")
    public String ping() {
        return "Tracking service is running correctly !";
    }
}
