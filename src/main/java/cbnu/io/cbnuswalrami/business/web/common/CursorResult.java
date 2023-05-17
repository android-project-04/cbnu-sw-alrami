package cbnu.io.cbnuswalrami.business.web.common;

import java.util.List;

public class CursorResult<T> {

    private List<T> values;

    private Boolean hasNext;

    private Long lastIndex;


    private CursorResult(List<T> values, Boolean hasNext, Long lastIndex) {
        this.values = values;
        this.hasNext = hasNext;
        this.lastIndex = lastIndex;
    }

    public static CursorResult from(List values, Boolean hasNext, Long lastIndex) {
        hasNext = hasNext == null ? false : hasNext;
        validate(lastIndex);
        return new CursorResult(values, hasNext, lastIndex);
    }

    private static void validate(Long lastIndex) {
        if (lastIndex < 0) {
            throw new IllegalArgumentException("lastIndex의 값은 음수면 안됩니다.");
        }
    }

    public List<T> getValues() {
        return values;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public Long getLastIndex() {
        return lastIndex;
    }
}
