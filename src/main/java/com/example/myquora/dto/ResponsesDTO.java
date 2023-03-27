package com.example.myquora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponsesDTO {
    private List<ResponseDTO> responseDTOList;
}
