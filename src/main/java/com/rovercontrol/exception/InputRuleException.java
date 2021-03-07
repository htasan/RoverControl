package com.rovercontrol.exception;

public class InputReaderException extends Exception {

    public InputReaderException(String message) {
        super(message);
    }

    public InputReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
