package com.ascendingdc.training.project2020.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6819113384777669312L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String arg0) {
        super(arg0);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

}
