package cbnu.io.cbnuswalrami.test.unit.common;

import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("id값을 받는 객체의 단위테스트")
class RequestIdTest {

    @Test
    @DisplayName("Null id value throws IllegalArgumentException")
    void from_null_id_throws_exception() {
        Long nullId = null;

        assertThrows(IllegalArgumentException.class, () -> RequestId.from(nullId));
    }

    @Test
    @DisplayName("Id value less than 1 throws IllegalArgumentException")
    void from_id_less_than_one_throws_exception() {
        Long invalidId = 0L;

        assertThrows(IllegalArgumentException.class, () -> RequestId.from(invalidId));
    }

    @Test
    @DisplayName("Valid id value returns RequestId object")
    void from_valid_id_returns_request_id() {
        Long validId = 1L;

        RequestId requestId = RequestId.from(validId);

        assertNotNull(requestId);
        assertEquals(validId, requestId.getId());
    }

    @Test
    @DisplayName("RequestId toString returns string representation of id")
    void to_string_returns_string_representation_of_id() {
        Long validId = 1L;

        RequestId requestId = RequestId.from(validId);

        assertEquals(validId.toString(), requestId.toString());
    }
}
