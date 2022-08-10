package com.personia.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {

    private final String jwt;


    public String getJwt() {
        return jwt;
    }
}
