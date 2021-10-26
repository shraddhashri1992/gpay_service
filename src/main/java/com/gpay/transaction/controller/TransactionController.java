package com.gpay.transaction.controller;

import com.gpay.transaction.dto.TransactionDto;
import com.gpay.transaction.dto.TransactionResponseDto;
import com.gpay.transaction.entity.Transaction;
import com.gpay.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/transaction")
    public ResponseEntity<String> performTransactionByPhoneNumber(@Valid @RequestBody TransactionDto transactionDto) {
        ResponseEntity<String> response= transactionService.performTransaction(transactionDto);
        LOGGER.info("Record Inserted Successfully");
        return  response;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getStatement(@Validated @NotNull @RequestParam Long userId) {
        List<Transaction> transactionDetailsList=transactionService.getStatement(userId);
        return new ResponseEntity<List<Transaction>>(transactionDetailsList, HttpStatus.OK);
    }

}
