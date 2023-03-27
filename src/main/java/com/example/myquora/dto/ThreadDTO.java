package com.example.myquora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ThreadDTO {
    private Integer threadId;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private String username;
    private String email;
}
