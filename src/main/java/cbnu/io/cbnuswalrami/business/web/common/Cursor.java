package cbnu.io.cbnuswalrami.business.web.common;

import lombok.Getter;
import java.util.Objects;

@Getter
public class Cursor {

    public static final Long DEFAULT_SIZE = 10L;

    private Long next;
    private Long size;

    private Cursor(Long next, Long size) {
        this.next = next;
        this.size = size;
    }

    public static Cursor from(Long next, Long size) {
        size = size == null ? DEFAULT_SIZE : size;
        validate(next);
        return new Cursor(next, size);
    }

    private static void validate(Long next) {
        if (next == null) {
            return;
        }

        if (next < 0L) {
            throw new IllegalArgumentException("next값을 양수로 주십시오.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cursor cursor)) return false;
        return getSize() == cursor.getSize() && getNext().equals(cursor.getNext());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNext(), getSize());
    }

    @Override
    public String toString() {
        return String.format("Next: %s, Cursor: %s", next, size);
    }
}
