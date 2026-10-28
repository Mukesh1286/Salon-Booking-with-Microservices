package com.mukesh.user.service.service;

import com.mukesh.user.service.payload.dto.SignupDTO;
import com.mukesh.user.service.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;

}
