package com.mukesh.user.service.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private String message;
    private String error;
    private LocalDateTime timestamp;
}
