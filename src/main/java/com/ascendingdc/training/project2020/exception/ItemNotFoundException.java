package com.ascendingdc.training.project2020.exception;

import java.io.Serial;
import java.io.Serializable;

public class ItemNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6621182640863473898L;

    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String arg0) {
        super(arg0);
    }

    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }

    public ItemNotFoundException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

}
