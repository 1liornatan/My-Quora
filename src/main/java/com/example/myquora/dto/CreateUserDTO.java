package com.example.myquora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
