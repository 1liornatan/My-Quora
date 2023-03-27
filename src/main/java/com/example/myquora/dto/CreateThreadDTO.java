package com.example.myquora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateThreadDTO {
    private String title;
    private String content;
    private String username;
}
