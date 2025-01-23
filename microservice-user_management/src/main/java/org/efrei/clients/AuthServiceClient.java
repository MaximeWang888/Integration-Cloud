package org.efrei.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authentification", url = "http://authentification:8081")
public interface AuthServiceClient {
    @GetMapping("/auth/ping/{token}")
    String ping(@RequestParam("token") String token);
    @GetMapping("/auth/checkConnection/{userId}")
    Boolean isUserLoggedIn(@RequestParam("userId") Long token);
}
