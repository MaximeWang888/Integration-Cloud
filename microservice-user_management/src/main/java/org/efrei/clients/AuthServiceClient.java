package org.efrei.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-authentification")
public interface AuthServiceClient {
    @GetMapping("/auth/ping/{token}")
    String ping(@RequestParam("token") String token);
}
