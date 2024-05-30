package com.clifm.se.capstone.aquariumbuddy.exceptions;

public class TankNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 7287810937539717143L;

    /**
     * Exception with no message or cause.
     */
    public TankNotFoundException() {
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public TankNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public TankNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public TankNotFoundException(Throwable cause) {
        super(cause);
    }
}
