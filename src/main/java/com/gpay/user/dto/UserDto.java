package com.gpay.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    @NotNull
    @Min(10)
    private String phoneNumber;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String gender;
    @NotNull
    @Min(18)
    private  Integer age;

}
