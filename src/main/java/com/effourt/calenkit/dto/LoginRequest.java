package com.effourt.calenkit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String id;
    private String password;
    private String loginCode;
    private String registerCode;
    private String initializeCode;
}
