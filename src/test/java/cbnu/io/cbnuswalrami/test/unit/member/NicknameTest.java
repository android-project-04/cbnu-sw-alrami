package cbnu.io.cbnuswalrami.test.unit.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("닉네임 단위테스트")
class NicknameTest {

    @Test
    @DisplayName("Null nickname value throws IllegalArgumentException")
    void from_null_nickname_throws_exception() {
        String nullNickname = null;

        assertThrows(IllegalArgumentException.class, () -> Nickname.from(nullNickname));
    }

    @Test
    @DisplayName("Nickname value exceeding 39 characters throws IllegalArgumentException")
    void from_nickname_exceeds_length_throws_exception() {
        String longNickname = "a".repeat(40);

        assertThrows(IllegalArgumentException.class, () -> Nickname.from(longNickname));
    }

    @Test
    @DisplayName("Invalid nickname format throws IllegalArgumentException")
    void from_invalid_nickname_format_throws_exception() {
        String invalidNickname = "123";

        assertThrows(IllegalArgumentException.class, () -> Nickname.from(invalidNickname));
    }

    @Test
    @DisplayName("Valid nickname returns Nickname object")
    void from_valid_nickname_returns_nickname() {
        String validNickname = "validNickname";

        Nickname nickname = Nickname.from(validNickname);

        assertNotNull(nickname);
        assertEquals(validNickname, nickname.getNickname());
    }

    @Test
    @DisplayName("Nickname toString returns string representation of nickname")
    void to_string_returns_string_representation_of_nickname() {
        String validNickname = "validNickname";

        Nickname nickname = Nickname.from(validNickname);

        assertEquals(validNickname, nickname.toString());
    }
}
