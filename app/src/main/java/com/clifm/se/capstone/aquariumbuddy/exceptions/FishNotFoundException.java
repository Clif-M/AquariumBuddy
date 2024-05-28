package com.clifm.se.capstone.aquariumbuddy.exceptions;

public class FishNotFoundException  extends RuntimeException {
    private static final long serialVersionUID = -1759712672191874059L;

    public FishNotFoundException() {
    }

    public FishNotFoundException(String message) {
        super(message);
    }

    public FishNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FishNotFoundException(Throwable cause) {
        super(cause);
    }

    public FishNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
