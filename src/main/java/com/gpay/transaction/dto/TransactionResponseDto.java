package com.gpay.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TransactionResponseDto {

        private Long transactionId;
        private String phoneNumber;
        private String fromPhoneNumber;
        private String toPhoneNumber;
        private Double transferAmount;
        private String description;
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        private LocalDateTime transactionDate;

    }
