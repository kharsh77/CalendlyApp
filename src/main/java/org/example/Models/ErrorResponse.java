package org.example.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    boolean error = true;
    String message;

    public ErrorResponse(String message){
        this.message = message;
    }
}
