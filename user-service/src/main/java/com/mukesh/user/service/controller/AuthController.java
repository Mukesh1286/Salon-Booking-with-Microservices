package com.mukesh.user.service.controller;


import com.mukesh.user.service.payload.dto.LoginDTO;
import com.mukesh.user.service.payload.dto.SignupDTO;
import com.mukesh.user.service.payload.response.ApiResponse;
import com.mukesh.user.service.payload.response.ApiResponseBody;
import com.mukesh.user.service.payload.response.AuthResponse;
import com.mukesh.user.service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse> HomeControllerHandler() {

        return ResponseEntity.status(
                        HttpStatus.OK)
                .body(new ApiResponse(

                        "welcome to zosh property booking system, user api"

                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseBody<AuthResponse>> signupHandler(
            @Valid @RequestBody SignupDTO req) throws Exception {

        System.out.println("signup dto "+req);
        AuthResponse response=authService.signup(req);

        return ResponseEntity.ok(new ApiResponseBody<>(
                true,
                "User created successfully", response)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseBody<AuthResponse>> loginHandler(
            @RequestBody LoginDTO req) throws Exception {

        AuthResponse response=authService.login(req.getEmail(), req.getPassword());

        return ResponseEntity.ok(new ApiResponseBody<>(
                true,
                "User logged in successfully",
                response)
        );
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<ApiResponseBody<AuthResponse>> getAccessTokenHandler(
            @PathVariable String refreshToken) throws Exception {

        AuthResponse response = authService.getAccessTokenFromRefreshToken(refreshToken);

        return ResponseEntity.ok(new ApiResponseBody<>(
                true,
                "refresh token received successfully",
                response
        ));
    }
}
