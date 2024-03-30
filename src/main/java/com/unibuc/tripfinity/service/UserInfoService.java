package com.unibuc.tripfinity.service;

import com.unibuc.tripfinity.mapper.UserInfoMapper;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.UserInfoDTO;
import com.unibuc.tripfinity.model.UserInfoDetails;
import com.unibuc.tripfinity.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_USER");
        repository.save(userInfo);
        return "User Added Successfully";
    }

    public UserInfoDTO getUserProfile(String username){
        Optional<UserInfo> userInfoOpt = repository.findByUsername(username);
        if (userInfoOpt.isPresent()){
            return userInfoMapper.mapToDTO(userInfoOpt.get());
        }
        else {
            return null;
        }
    }

}
