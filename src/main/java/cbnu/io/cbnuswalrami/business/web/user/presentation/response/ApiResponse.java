package cbnu.io.cbnuswalrami.business.web.user.presentation.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public final class ApiResponse<T> {

    private final LocalDateTime eventTime;
    private HttpStatus status;
    private final Integer code;
    private T data;

    private ApiResponse(HttpStatus status, T data) {
        this.eventTime = LocalDateTime.now();
        this.status = status;
        this.code = status.value();
        this.data = data;
    }

    private ApiResponse(T data) {
        this.eventTime = LocalDateTime.now();
        this.status = HttpStatus.OK;
        this.code = HttpStatus.OK.value();
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> of(T data, HttpStatus status) {
        return new ApiResponse<>(status, data);
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
