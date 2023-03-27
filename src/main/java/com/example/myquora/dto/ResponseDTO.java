package com.example.myquora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseDTO {
    private String username;
    private String email;
    private LocalDateTime localDateTime;
    private String content;

}
