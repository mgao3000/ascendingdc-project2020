package com.ascendingdc.training.project2020.exception;

public class ItemNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1415548448625453136L;

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
