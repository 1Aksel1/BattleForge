package com.battleforge.backend.exceptions;

public class ActiveRunConflictException extends RuntimeException {
    public ActiveRunConflictException(String message) {
        super(message);
    }
}
