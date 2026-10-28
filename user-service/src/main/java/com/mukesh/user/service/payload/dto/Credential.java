package com.mukesh.user.service.payload.dto;

import lombok.Data;

@Data
public class Credential {

        private String type;
        private String value;
        private boolean temporary;

}
