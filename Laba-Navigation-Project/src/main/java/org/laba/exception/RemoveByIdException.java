package org.laba.exception;

import java.util.Objects;

/**
 * The MapperException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages and to link to our documentation.
 *
 *
 */
public class RemoveByIdException extends Exception {

    private static final long serialVersionUID = 11L;
    private final long errorCode;

    public static RemoveByIdException wrap(Exception exception, long errorCode) {
        if (exception instanceof RemoveByIdException) {
            RemoveByIdException se = (RemoveByIdException) exception;
            if (Objects.isNull(errorCode) != true && errorCode != se.getErrorCode()) {
                return new RemoveByIdException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new RemoveByIdException(exception.getMessage(), exception, errorCode);
        }
    }

    public RemoveByIdException(long errorCode) {
        this.errorCode = errorCode;
    }

    public RemoveByIdException(String message, long errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RemoveByIdException(Throwable cause, long errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public RemoveByIdException(String message, Throwable cause, long errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public long getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "Exception message: " + getMessage() + "\n Error code: " + errorCode;
    }
}