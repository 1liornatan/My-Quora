package com.example.myquora.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "username")
    private String username;
}
