package org.laba.exception;

import java.util.Objects;
import static org.laba.exception.Error.SAVE_ERROR;

/**
 * The SaveException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages and to link to our documentation.
 *
 *
 */
public class SaveException extends Exception {

    private static final long serialVersionUID = 11L;
    private final long errorCode;

    public static SaveException wrap(Exception exception, long errorCode) {
        if (exception instanceof SaveException) {
            SaveException se = (SaveException) exception;
            if (Objects.isNull(errorCode) != true && errorCode != se.getErrorCode()) {
                return new SaveException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new SaveException(exception.getMessage(), exception, errorCode);
        }
    }

    public SaveException(long errorCode) {
        this.errorCode = errorCode;
    }

    public SaveException(String message, long errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public SaveException(Throwable cause, long errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public SaveException(String message, Throwable cause, long errorCode) {
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
