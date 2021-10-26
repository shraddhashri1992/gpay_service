package com.gpay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "http://BANK-SERVICE/bank/users")
public interface UserClient {

    @GetMapping("/verifyUser/{phoneNumber}")
    public boolean verifyPhoneNumber( @PathVariable String phoneNumber);

}

