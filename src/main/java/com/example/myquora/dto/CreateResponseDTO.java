package com.example.myquora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateResponseDTO {
    private Integer threadId;
    private String content;
    private String username;
}