package com.gpay.user.service;


import com.gpay.constant.GlobalConstant;
import com.gpay.exception.ConnectionRefuseException;
import com.gpay.exception.UserNotFoundException;
import com.gpay.feign.UserClient;
import com.gpay.user.dto.UserDto;
import com.gpay.user.entity.User;
import com.gpay.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserClient userClient;

    public boolean saveUser(UserDto userDto) {
        Optional<User> userData = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
        boolean isExist = false;
        try {
                isExist = userClient.verifyPhoneNumber(userDto.getPhoneNumber());
            } catch (Exception e) {
                throw new ConnectionRefuseException(GlobalConstant.ERROR_CONNECTION_REFUSE);
            }
            if (isExist) {
                User user = new User();
                BeanUtils.copyProperties(userDto, user);
                userRepository.save(user);
            } else
                throw new UserNotFoundException(GlobalConstant.ERROR_MESSAGE_USER_IS_FOUND);

            return  isExist;
    }
    }



