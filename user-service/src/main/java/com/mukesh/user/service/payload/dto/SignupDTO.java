package com.mukesh.user.service.payload.dto;

import com.mukesh.user.service.domain.UserRole;
import lombok.Data;

@Data
public class SignupDTO {
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private String username;
    private UserRole role;

}
