package org.laba.exception;

import java.util.Objects;

import static org.laba.exception.Error.UPDATE_ERROR;

/**
 * The UpdateException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages and to link to our documentation.
 *
 *
 */
public class UpdateException extends Exception {

    private static final long serialVersionUID = 11L;
    private final long errorCode;

    public static UpdateException wrap(Throwable exception, long errorCode) {
        if (exception instanceof UpdateException) {
            UpdateException se = (UpdateException) exception;
            if (Objects.isNull(errorCode) != true && errorCode != se.getErrorCode()) {
                return new UpdateException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new UpdateException(exception.getMessage(), exception, errorCode);
        }
    }

    public UpdateException(long errorCode) {
        this.errorCode = errorCode;
    }

    public UpdateException(String message, long errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UpdateException(Throwable cause, long errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public UpdateException(String message, Throwable cause, long errorCode) {
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
