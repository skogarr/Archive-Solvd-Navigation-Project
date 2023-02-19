package org.laba.exception;

import java.util.Objects;

import static org.laba.enums.Error.SAVE_ERROR;

public class SaveException extends Exception {

    private static final long serialVersionUID = 11L;
    private final long errorCode;

    public static SaveException wrap(Throwable exception, long errorCode) {
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
        return SAVE_ERROR.getErrorCode();
    }
}
