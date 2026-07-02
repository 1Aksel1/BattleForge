package com.battleforge.backend.exceptions;

public class InvalidBattleStateException extends RuntimeException {

    public InvalidBattleStateException(String message) {
        super(message);
    }
}
