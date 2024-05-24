package org.efrei.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Service
@FeignClient(name = "microservice-authentification")
public interface AuthServiceClient {
    @GetMapping("/auth/ping/{userId}")
    Boolean isUserLoggedIn(@RequestParam("userId") Long token);
}
