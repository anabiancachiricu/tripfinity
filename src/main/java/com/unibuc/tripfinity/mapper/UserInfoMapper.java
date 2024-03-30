package com.unibuc.tripfinity.mapper;

import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.UserInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {

    public UserInfoDTO mapToDTO (UserInfo userInfo){
        return UserInfoDTO.builder().firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .username(userInfo.getUsername())
                .email(userInfo.getEmail())
                .birthDate(userInfo.getBirthDate())
                .build();
    }
}
