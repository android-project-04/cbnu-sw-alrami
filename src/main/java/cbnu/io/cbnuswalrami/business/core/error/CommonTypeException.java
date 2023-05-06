package cbnu.io.cbnuswalrami.business.core.error;

import cbnu.io.cbnuswalrami.common.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public enum CommonTypeException implements BaseException {

    NUMBER_FORMAT_EXCEPTION(400, "회원 아이디가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    LOGIN_FORBIDDEN_EXCEPTION(403, "일정 기간 내 로그인 시도 횟수를 초과했습니다.", HttpStatus.FORBIDDEN),
    NULL_EXCEPTION(400, "null값을 입력했습니다.", HttpStatus.BAD_REQUEST),
    EXIST_USER(400, "이미 존재하는 유저입니다.", HttpStatus.BAD_REQUEST),
    REDIS_WRONG_TYPE_DATASTRUCTURE_EXCEPTION(502, "올바르지 않은 키를 입력했습니다.", HttpStatus.BAD_GATEWAY),
    NOT_FOUNT_USER(400, "존재하지 않는 id의 유저입니다.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400, "패스워드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    REDIS_CONNECTION_FAILURE_EXCEPTION(502, "서버에 문제가 발생했습니다.", HttpStatus.BAD_GATEWAY);


    private final int code;
    private final String message;
    private final HttpStatus status;

    CommonTypeException(
            int code,
            String message,
            HttpStatus status
    ) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
