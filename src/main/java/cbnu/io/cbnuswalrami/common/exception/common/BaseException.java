package cbnu.io.cbnuswalrami.common.exception.common;

import org.springframework.http.HttpStatus;

public interface BaseException {

    int getCode();

    String getMessage();

    HttpStatus getStatus();
}
