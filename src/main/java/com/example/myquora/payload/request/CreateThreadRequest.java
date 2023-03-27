package com.example.myquora.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateThreadRequest {
    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}
