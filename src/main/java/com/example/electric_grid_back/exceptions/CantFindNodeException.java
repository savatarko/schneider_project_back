package com.example.electric_grid_back.exceptions;

import org.springframework.http.HttpStatus;

public class CantFindNodeException extends CustomException{

    public CantFindNodeException() {
        super("Can't find node with given id", ErrorCode.NODE_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }
}
