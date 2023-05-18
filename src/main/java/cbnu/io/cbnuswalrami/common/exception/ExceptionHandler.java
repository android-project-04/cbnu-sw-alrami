package cbnu.io.cbnuswalrami.common.exception;

import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import cbnu.io.cbnuswalrami.common.exception.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse catchIllegalArgumentException(IllegalArgumentException exception) {
        log.info("IllegalArgumentException: {}", exception.getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CbnuException.class)
    public ErrorResponse catchCbnuException(CbnuException exception) {
        log.info("CbnuException: {}", exception.getMessage());
        return ErrorResponse.of(exception);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ErrorResponse catchRuntimeException(IllegalArgumentException exception) {
        log.info("RuntimException: {}", exception.getMessage());
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
