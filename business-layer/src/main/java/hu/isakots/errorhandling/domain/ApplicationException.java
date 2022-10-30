package hu.isakots.errorhandling.domain;

import java.util.UUID;

public class ApplicationException extends RuntimeException {
    private final String uniqueId = UUID.randomUUID().toString();
    private final ErrorCodeEnum errorCode;

    public ApplicationException(ErrorCodeEnum errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(ErrorCodeEnum errorCode, String message, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }
}
