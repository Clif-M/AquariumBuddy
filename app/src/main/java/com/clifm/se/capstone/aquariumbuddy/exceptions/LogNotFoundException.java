package com.clifm.se.capstone.aquariumbuddy.exceptions;

public class LogNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 777771312531450073L;

    /**
     * Exception with no message or cause.
     */
    public LogNotFoundException() {
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public LogNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public LogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public LogNotFoundException(Throwable cause) {
        super(cause);
    }
}
