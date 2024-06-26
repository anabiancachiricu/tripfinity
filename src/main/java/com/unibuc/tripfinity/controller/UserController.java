package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.AuthRequest;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.UserInfoDTO;
import com.unibuc.tripfinity.service.EmailService;
import com.unibuc.tripfinity.service.JwtService;
import com.unibuc.tripfinity.service.S3Service;
import com.unibuc.tripfinity.service.UserInfoService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping(value = "/addNewUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) throws JSONException {
        try{
            String responseMessage = service.addUser(userInfo);
            return new ResponseEntity<>(new JSONObject().put("message", responseMessage).toString(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new JSONObject().put("message", e.getMessage()).toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserInfoDTO userProfile(Authentication authentication) {
        //TODO: get user profile
        String username = authentication.getName();
//        emailService.sendMail();
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

    @PostMapping("/user/updateUserProfile")
    public ResponseEntity<UserInfoDTO> updateUser(@RequestBody UserInfoDTO userInfoDTO){
        System.out.println("IN UPDATE USER");
        try{
            UserInfoDTO responseMessage = service.updateProfile(userInfoDTO);
            System.out.println("IN UPDATE USER TRY");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println("IN UPDATE USER ERROR "+ e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String eTag = s3Service.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully, ETag: " + eTag);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }




}
