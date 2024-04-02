package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.AuthRequest;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.UserInfoDTO;
import com.unibuc.tripfinity.service.JwtService;
import com.unibuc.tripfinity.service.UserInfoService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) throws JSONException, IOException {
        String responseMessage = service.addUser(userInfo);
        return new ResponseEntity<>(new JSONObject().put("message", responseMessage).toString(), HttpStatus.OK);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserInfoDTO userProfile(Authentication authentication) {
        //TODO: get user profile
        String username = authentication.getName();
        return service.getUserProfile(username);
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws JSONException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String responseMessage =  jwtService.generateToken(authRequest.getUsername());
            return new ResponseEntity<>(new JSONObject().put("message", responseMessage).toString(), HttpStatus.OK);
        } else {
            String responseMessage = "Invalid user or password";
            return new ResponseEntity<>(new JSONObject().put("message", responseMessage).toString(), HttpStatus.BAD_REQUEST);
        }
    }

}
