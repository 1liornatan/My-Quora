package com.example.myquora.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateResponseRequest {
    @JsonProperty("content")
    private String content;
}
