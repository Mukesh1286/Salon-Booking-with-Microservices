package com.mukesh.user.service.payload.dto;

import lombok.Data;

@Data
public class KeycloakUserinfo {

    private String sub;
    private String email;
    private String preferred_username;

}
