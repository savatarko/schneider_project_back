package com.example.electric_grid_back.exceptions;

import org.springframework.http.HttpStatus;

public class NodeCantBeDeletedException extends CustomException{
    public NodeCantBeDeletedException() {
        super("You cant delete the source!", ErrorCode.SOURCE_DELETION, HttpStatus.BAD_REQUEST);
    }
}
