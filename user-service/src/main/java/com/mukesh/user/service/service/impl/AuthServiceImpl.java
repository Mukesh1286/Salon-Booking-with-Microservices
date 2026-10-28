package com.mukesh.user.service.service.impl;

import com.mukesh.user.service.model.User;
import com.mukesh.user.service.payload.dto.SignupDTO;
import com.mukesh.user.service.payload.response.AuthResponse;
import com.mukesh.user.service.payload.response.TokenResponse;
import com.mukesh.user.service.repository.UserRepository;
import com.mukesh.user.service.service.AuthService;
import com.mukesh.user.service.service.KeyclockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final KeyclockService keyclockService;



    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse=keyclockService.getAdminAccessToken(
                username,
                password,
                "password",
                null
        );
        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome Back " + username);
        response.setMessage("login success");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }


    @Override
    public AuthResponse signup(SignupDTO req) throws Exception {
        keyclockService.createUser(req);

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setPhone(req.getPhone());
        createdUser.setRole(req.getRole());
        createdUser.setFullName(req.getFullName());
        createdUser.setUsername(req.getUsername());
        userRepository.save(createdUser);


        TokenResponse tokenResponse= keyclockService.getAdminAccessToken(
                req.getUsername(),
                req.getPassword(),
                "password",
                null
        );

        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome " + createdUser.getEmail());
        response.setMessage("Register success");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse= keyclockService.getAdminAccessToken(
                null,
                null,
                "refresh_token",
                refreshToken
        );
        AuthResponse response = new AuthResponse();

        response.setMessage("Access token received");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }
}
