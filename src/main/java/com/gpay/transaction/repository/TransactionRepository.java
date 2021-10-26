package com.gpay.transaction.repository;

import com.gpay.transaction.entity.Transaction;
import com.gpay.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {



    List<Transaction> findByPhoneNumber(String phoneNumber);
}
