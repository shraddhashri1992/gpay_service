package com.gpay.user.service;


import com.gpay.user.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public boolean saveUser(UserDto userDetailsDto);

}
