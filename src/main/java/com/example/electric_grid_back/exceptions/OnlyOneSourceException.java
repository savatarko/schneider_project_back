package com.example.electric_grid_back.exceptions;

import org.springframework.http.HttpStatus;

public class OnlyOneSourceException extends CustomException{
    public OnlyOneSourceException() {
        super("Only one source is allowed", ErrorCode.MULTIPLE_SOURCES, HttpStatus.BAD_REQUEST);
    }
}
