package com.diploma.parser;

/**
 * User: d.ulanovych
 * Date: 11/16/13
 */
public class UMLParserException extends Exception {
    public UMLParserException() {
        super();
    }

    public UMLParserException(String message) {
        super(message);
    }

    public UMLParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UMLParserException(Throwable cause) {
        super(cause);
    }

    protected UMLParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
