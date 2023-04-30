package com.example.myquora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadDTO {
    private Long threadId;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private String username;
    private String email;
}
