package com.esportslan.microservices.live_updates.models;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExceptionBody {
    private HttpStatus httpStatus;
    private String message;
}
