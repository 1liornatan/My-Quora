package com.example.myquora.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "email")
    private String email;
}
