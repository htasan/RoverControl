package com.rovercontrol.exception;

public class InputRuleException extends Exception {

    public InputRuleException(String message) {
        super(message);
    }

    public InputRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
