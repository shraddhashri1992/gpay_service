package com.gpay.transaction.service;

import com.gpay.transaction.dto.TransactionDto;
import com.gpay.transaction.dto.TransactionResponseDto;
import com.gpay.transaction.entity.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {


    ResponseEntity<String> performTransaction(TransactionDto transactionDto);

    List<Transaction> getStatement(Long userId);
}
