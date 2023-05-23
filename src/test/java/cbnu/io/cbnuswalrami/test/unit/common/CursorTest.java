package cbnu.io.cbnuswalrami.test.unit.common;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("커서 단위 테스트")
public class CursorTest {

    @DisplayName("커서에 size를 안넣으면 10이 기본값이다.")
    @Test
    public void given_when_then() {
        // given
        Cursor cursor = Cursor.from(10L, null);

        // when
        Long actual = cursor.getSize();

        // then
        assertThat(actual).isEqualTo(10L);
    }

    @DisplayName("커서로 next값이 마이너스가 들어오면 예외를 반환한다.")
    @Test
    public void given_minus_next_when_create_cursor_then_ex() {
        // given
        Long next = -1L;

        // then
        assertThrows(IllegalArgumentException.class, () -> Cursor.from(next, 10L));
    }

    @DisplayName("커서 size가 1 미만으로 들어오면 디폴트 사이즈인 size를 10으로 반환한다.")
    @Test
    public void given_minus_size_when_create_cursor_then_size_10() {
        // given
        Long size = -1L;

        // when
        Long actual = Cursor.from(null, size).getSize();

        // then
        assertThat(actual).isEqualTo(10L);

    }
}
