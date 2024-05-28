package com.clifm.se.capstone.aquariumbuddy.exceptions;

public class FishNotFoundException  extends RuntimeException {
    private static final long serialVersionUID = -1759712672191874059L;

    /**
     * Exception with no message or cause.
     */
    public FishNotFoundException() {
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public FishNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public FishNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public FishNotFoundException(Throwable cause) {
        super(cause);
    }
}
