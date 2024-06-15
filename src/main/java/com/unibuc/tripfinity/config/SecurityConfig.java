package com.unibuc.tripfinity.config;

import com.unibuc.tripfinity.filter.JwtAuthFilter;
import com.unibuc.tripfinity.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    @Autowired
    private JwtAuthFilter authFilter;

    // User Creation
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoService();
    }

    // Configuring HttpSecurity
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .cors().configurationSource( new CorsConfigurationSource(){
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//                config.setAllowedMethods(Collections.singletonList("*"));
//                config.setAllowCredentials(true);
//                config.setAllowedHeaders(Collections.singletonList("*"));
//                config.setMaxAge(3600L);
//                return config;
//            }
//        }).and()
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated()
//                .and()
//                .authorizeHttpRequests().anyRequest().authenticated()
//                .and()
//                .oauth2Login().defaultSuccessUrl("http://localhost:4200/", true)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();

//        .csrf().disable()
//                .authorizeRequests()
//                // Allow access to these URLs without authentication
//                .requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll()
//                // Require authentication for URLs starting with "/auth/user/"
//                .requestMatchers("/auth/user/**").authenticated()
//                // Require authentication for URLs starting with "/auth/admin/"
//                .requestMatchers("/auth/admin/**").authenticated()
//                // For other URLs, require authentication
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login()
//                .defaultSuccessUrl("http://localhost:4200/")
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                // Customize authentication mechanism for Google login
//                .requestMatchers("/login/google").permitAll()
//                .requestMatchers("/login/facebook").permitAll()
//                .and().build();
    // Allow access to Facebook login endpoint


//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.addAllowedOrigin("http://localhost:8080");
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setMaxAge(3600L);
                    return config;
                });

        return http.csrf().disable().build();
    }

    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll()
                .requestMatchers("/auth/user/**").authenticated()
                .requestMatchers("/auth/admin/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/auth/login")
                .defaultSuccessUrl("/auth/oauth2/success")
                .userInfoEndpoint()
                .userService(oAuth2UserService());

        return http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilterChain socialLoginFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .requestMatchers("auth/login/google", "/login/facebook").permitAll()
                .and().build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new DefaultOAuth2UserService();
    }



    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}