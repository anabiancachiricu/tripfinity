package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.AuthRequest;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.UserInfoDTO;
import com.unibuc.tripfinity.service.JwtService;
import com.unibuc.tripfinity.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.logging.Logger;
import java.util.logging.Level;

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
    private ClientRegistrationRepository clientRegistrationRepository;


    private static final Logger log = Logger.getLogger(UserController.class.getName());


    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<String> getOAuth2Success(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String token = jwtService.generateToken(oAuth2User.getName());
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/user1")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User user){
        return user.getAttributes();
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) throws JSONException {
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

    // Endpoint for social login with Google
    @CrossOrigin(origins = {"http://localhost:4200","http://localhost:8080"} )
    @PostMapping("/login/google")
    public ResponseEntity<String> loginWithGoogle(HttpServletRequest request, @RequestBody Map<String, String> googleCredentials) {
        String redirectUri = "http://localhost:8080/auth/login/google/oauth2/code/google";

        // Build the OAuth2 authorization request
        OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.authorizationCode();
        builder.clientId("358584780474-n7n060qsdvjsufantlg5uette6ope0p5.apps.googleusercontent.com");
        builder.authorizationUri("https://accounts.google.com/o/oauth2/auth");
        builder.redirectUri(redirectUri);
        builder.scope("openid", "profile", "email"); // Customize scopes as needed
        OAuth2AuthorizationRequest authorizationRequest = builder.build();

        // Save the authorization request in the session
        request.getSession().setAttribute(OAuth2AuthorizationRequest.class.getName(), authorizationRequest);

        // Redirect the user to the Google sign-up page
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, authorizationRequest.getAuthorizationRequestUri())
                .build();
    }


    @CrossOrigin(origins = {"http://localhost:4200","http://localhost:8080"} )
    @GetMapping("/login/google/oauth2/code/google")
    public ResponseEntity<String> handleGoogleRedirect(@RequestParam("code") String code) {
        // Exchange the authorization code for an access token
        // You can use Spring Security's OAuth2 client support to handle this
        // Once authenticated, generate JWT token and return it
        // Example implementation:

        String token = jwtService.generateToken(code);
        log.info("Google token: " + token);
        return ResponseEntity.ok().body(token);
    }

    // Endpoint for social login with Facebook
    @PostMapping("/login/facebook")
    public ResponseEntity<String> loginWithFacebook(@RequestBody Map<String, String> facebookCredentials) throws JSONException {
        // Implement logic to handle Facebook login
        // You can use Facebook's OAuth2 authentication here
        // Once authenticated, generate JWT token and return it
//         Example implementation:
        String token = jwtService.generateToken(facebookCredentials.get("facebookAccessToken"));
        log.info("Facebook token: "+ token);
        return ResponseEntity.ok().body(token);
    }




}