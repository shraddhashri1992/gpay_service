package com.gpay.user.controller;


import com.gpay.user.dto.UserDto;
import com.gpay.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService usersService;
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto) {
      boolean isExist= usersService.saveUser(userDto);
        LOGGER.info("Record Inserted Successfully");
        if(isExist)
        return  ResponseEntity.status(HttpStatus.OK).body("Record Inserted Successfully"+" "+"Phone Number = "+userDto.getPhoneNumber());
        else
            return  ResponseEntity.status(HttpStatus.OK).body("Fail to insert data as given number" +userDto.getPhoneNumber()+" " +"Is not link with any bank account");

    }





    }
