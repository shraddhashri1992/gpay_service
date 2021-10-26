package com.gpay.feign;

import com.gpay.transaction.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient("http://BANK-SERVICE/bank/transactions")
public interface TransactionClient {

    @PostMapping ("/gpayTransaction")
    ResponseEntity<String> performTransactionByPhoneNumber(@RequestBody TransactionDto transactionDto);

    @GetMapping("/status")
    public boolean checkSeverStatus();
}