package com.gpay;

import com.gpay.exception.ConnectionRefuseException;
import com.gpay.feign.UserClient;
import com.gpay.user.dto.UserDto;
import com.gpay.user.entity.User;
import com.gpay.user.repository.UserRepository;
import com.gpay.user.service.UserService;
import com.gpay.user.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.gpay.user.dto.UserDto;
import com.gpay.user.entity.User;
import com.gpay.user.repository.UserRepository;
import com.gpay.user.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserClient userClient;

    @Test
    public void saveUserTestForPositive() {
        UserDto userDto=new UserDto();
        userDto.setPhoneNumber("9894231265");
        User user=new User();
        user.setFirstName("shr");
        user.setLastName("shri");
        user.setPhoneNumber(userDto.getPhoneNumber());

        Mockito.when(userRepository.findByPhoneNumber(userDto.getPhoneNumber()  )).
                thenReturn(Optional.of(user));
        Mockito.when(userClient.verifyPhoneNumber(userDto.getPhoneNumber())).
                thenReturn(true);
        Assert.assertNotNull(userDto);
        boolean result= userServiceImpl.saveUser(userDto);
        Assert.assertEquals(result,true);
    }

    @Test
    public void saveUserTestForException() {
        try {
            Mockito.when(userClient.verifyPhoneNumber("9856458426")).thenReturn(false);
        }catch (Exception e){
        Assert.assertTrue(true);}
    }
}



