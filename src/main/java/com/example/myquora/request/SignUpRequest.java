package com.example.myquora.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignUpRequest {

    @JsonProperty(value = "first-name")
    private String firstName;

    @JsonProperty(value = "last-name")
    private String lastName;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "email")
    private String email;
}
