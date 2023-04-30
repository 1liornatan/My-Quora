package com.example.myquora.payload.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class QuoraConflictResponse {
    private String message;
}
