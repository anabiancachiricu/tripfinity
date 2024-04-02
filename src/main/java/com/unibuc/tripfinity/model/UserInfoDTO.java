package com.unibuc.tripfinity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserInfoDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String details;
}
