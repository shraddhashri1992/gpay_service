package com.gpay.transaction.service;

import com.gpay.constant.GlobalConstant;
import com.gpay.exception.ConnectionRefuseException;
import com.gpay.exception.GlobalException;
import com.gpay.exception.UserNotFoundException;
import com.gpay.feign.TransactionClient;
import com.gpay.transaction.dto.TransactionDto;
import com.gpay.transaction.dto.TransactionResponseDto;
import com.gpay.transaction.entity.Transaction;
import com.gpay.transaction.repository.TransactionRepository;
import com.gpay.user.entity.User;
import com.gpay.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

@Autowired
TransactionRepository transactionRepository;
    @Autowired
    TransactionClient transactionClient;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public ResponseEntity<String> performTransaction(TransactionDto transactionDto) {
    System.out.println(transactionDto);
    //CircuitBreaker circuitBreaker=circuitBreakerFactory.create("circuitbreaker");
    //boolean result=  circuitBreaker.run(()->transactionClient.checkSeverStatus(),throwable -> getDefaultInfo());
    ResponseEntity<String> transactionResponse=null;
try {
     transactionResponse = transactionClient.performTransactionByPhoneNumber(transactionDto);

    if (transactionResponse.getStatusCode().is2xxSuccessful()) {
        Transaction transactionFromPhoneNumber = new Transaction();
        transactionFromPhoneNumber.setTransactionDate(  LocalDateTime.now());
        transactionFromPhoneNumber.setStatus("Debited "+transactionDto.getTransferAmount());
        transactionFromPhoneNumber.setPhoneNumber(transactionDto.getFromPhoneNumber());
        transactionFromPhoneNumber.setToPhoneNumber(transactionDto.getToPhoneNumber());
        BeanUtils.copyProperties(transactionDto, transactionFromPhoneNumber);
        transactionRepository.save(transactionFromPhoneNumber);

        Transaction transactionToPhoneNumber = new Transaction();
        transactionToPhoneNumber.setTransactionDate(LocalDateTime.now());
        transactionToPhoneNumber.setPhoneNumber("Credited"+transactionDto.getTransferAmount());
        transactionToPhoneNumber.setPhoneNumber(transactionDto.getToPhoneNumber());
        transactionToPhoneNumber.setFromPhoneNumber(transactionDto.getFromPhoneNumber());
        BeanUtils.copyProperties(transactionDto, transactionToPhoneNumber);
        transactionRepository.save(transactionToPhoneNumber);
    }
    } catch(Exception e)
        {
            throw new ConnectionRefuseException(GlobalConstant.ERROR_CONNECTION_REFUSE);
            }
        return  transactionResponse;
        }

    @Override
    public List<Transaction> getStatement(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()) {
            String phoneNumber = user.get().getPhoneNumber();
            List<Transaction> transaction = transactionRepository.findByPhoneNumber(phoneNumber);
            return transaction;
        }else throw new UserNotFoundException("No use found for given id");
    }

    /*private  boolean  getDefaultInfo() {
        return  false;

    }*/
}
