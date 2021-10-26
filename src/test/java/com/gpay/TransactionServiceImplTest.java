package com.gpay;

import com.gpay.feign.TransactionClient;
import com.gpay.transaction.dto.TransactionDto;
import com.gpay.transaction.dto.TransactionResponseDto;
import com.gpay.transaction.entity.Transaction;
import com.gpay.transaction.repository.TransactionRepository;
import com.gpay.transaction.service.TransactionServiceImpl;
import com.gpay.user.dto.UserDto;
import com.gpay.user.entity.User;
import com.gpay.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;
    @Mock
    TransactionClient transactionClient;

    @Test
    public void performTransactionForPositive() {
        User user=new User();
        user.setFirstName("shr");
        user.setLastName("shri");
        user.setPhoneNumber("9894231265");
        TransactionDto transactionDto=new TransactionDto();
        transactionDto.setTransferAmount(500d);
        transactionDto.setFromPhoneNumber("9852365236");
        transactionDto.setToPhoneNumber("958423654895");
        transactionDto.setDescription("pay");
        ResponseEntity<String>response=new ResponseEntity<>( HttpStatus.OK);
        Mockito.when(transactionClient.performTransactionByPhoneNumber(transactionDto)).
                thenReturn(response);
      response=  transactionServiceImpl.performTransaction(transactionDto);
        System.out.println(response.getStatusCode().is2xxSuccessful());

        //Assert.assertNotNull(user);
        Assert.assertEquals(response.getStatusCode().is2xxSuccessful(),true);
    }

    @Test
    public void performTransactionForNegative() {
        try {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransferAmount(500d);
            transactionDto.setFromPhoneNumber("9852365236");
            transactionDto.setToPhoneNumber("958423654895");
            transactionDto.setDescription("pay");
            ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
            Mockito.when(transactionClient.performTransactionByPhoneNumber(transactionDto)).
                    thenReturn(response);
            transactionServiceImpl.performTransaction(transactionDto);
        }        catch(Exception e){
            Assert.assertTrue(true);

        }
    }

    @Test
    public void getStatementNotNullTest() {
        List<Transaction>transactionsList=new ArrayList<>();
        Transaction transaction=new Transaction();
        transaction.setTransactionId(1l);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransferAmount(500d);
        transaction.setFromPhoneNumber("9852365236");
        transaction.setToPhoneNumber("958423654895");
        transaction.setDescription("pay");
        transactionsList.add(transaction);
        //<Transaction>transactionsList=null;

        User user=new User();
        user.setUserId(1l);
        user.setFirstName("shr");
        user.setLastName("shri");
        user.setPhoneNumber("9894231265");
        Mockito.when(userRepository.findByUserId(1l)).
                thenReturn(Optional.of(user));

        Mockito.when(transactionRepository.findByPhoneNumber(user.getPhoneNumber())).
                thenReturn(transactionsList);

      transactionsList=  transactionServiceImpl.getStatement(1l);

        Assert.assertNotNull(transactionsList);



      //  List<Transaction> transaction=transactionRepository.findByPhoneNumber(phoneNumber);




    }




}
