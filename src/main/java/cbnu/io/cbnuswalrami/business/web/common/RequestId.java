package cbnu.io.cbnuswalrami.business.web.common;

import cbnu.io.cbnuswalrami.business.core.error.ErrorField;

public class RequestId {

    private Long id;

    private RequestId(Long id) {
        validateId(id);
        this.id = id;
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id 값은 null일 수 없습니다.", ErrorField.of("id", id));
        }
        if (id < 1) {
            throw new IllegalArgumentException("id 값은 1 이상이여야 합니다.", ErrorField.of("id", id));
        }
    }

    public static RequestId from(Long id) {
        return new RequestId(id);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
