package org.efrei.clients;

import org.efrei.entity.Listing;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "listing-service")
public interface ListingServiceClient {
    @GetMapping("/listings/{listingId}")
    Listing getListing(@PathVariable("listingId") Long listingId);
}
