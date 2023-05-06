package cbnu.io.cbnuswalrami.common.exception.common;

import org.springframework.http.HttpStatus;

public class CbnuException extends RuntimeException{

    private final BaseException baseException;

    private CbnuException(BaseException baseException) {
        super(baseException.getMessage());
        this.baseException = baseException;
    }

    public BaseException getBaseException() {
        return baseException;
    }

    public static CbnuException of(BaseException baseException) {
        return new CbnuException(baseException);
    }

    public int getCode() {
        return baseException.getCode();
    }

    public String getMessage() {
        return baseException.getMessage();
    }

    public HttpStatus getStatus() {
        return baseException.getStatus();
    }
}
