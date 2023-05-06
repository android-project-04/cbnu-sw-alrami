package cbnu.io.cbnuswalrami.test.unit;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.StudentNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("학번 단위 테스트")
public class StudentNumberTest {

    @DisplayName("10자리 이하의 학번을 입력하면 예외를 반환한다.")
    @Test
    public void given_short_student_number_when_create_then_ex() {
        // given
        Integer studentNumber = 202011011;

        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> StudentNumber.from(studentNumber));
        String message = exception.getMessage();
        assertEquals("10글자의 학번을 입력해주세요.", message);
    }

    @DisplayName("null값이 들어오면 예외를 반환한다.")
    @Test
    public void given_null_student_number_when_create_then_ex() {
        // given
        Integer studentNumber = null;

        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> StudentNumber.from(studentNumber));
        String message = exception.getMessage();
        assertEquals("학번을 입력하여 주세요.", message);
    }

    @DisplayName("10글자의 학번이 들어오면 예외를 던지지 않는다.")
    @Test
    public void given_when_then() {
        // given
        Integer studentNumber = 2020110110;

        // then
        assertEquals(studentNumber, StudentNumber.from(studentNumber).getStudentNumber());

    }
}
