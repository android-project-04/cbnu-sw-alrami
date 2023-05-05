package cbnu.io.cbnuswalrami.business.core.domon.user.entity.values;

import cbnu.io.cbnuswalrami.business.core.error.ErrorField;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class StudentNumber {

    private Integer studentNumber;

    protected StudentNumber() {
    }


    private StudentNumber(Integer studentNumber) {
        validateStudentNumber(studentNumber);
        this.studentNumber = studentNumber;
    }

    public static StudentNumber from(Integer studentNumber) {
        return new StudentNumber(studentNumber);
    }

    private void validateStudentNumber(Integer studentNumber) {
        if (null == studentNumber) {
            throw new IllegalArgumentException("학번을 입력하여 주세요.", ErrorField.of("studentNumber", studentNumber));
        }
        if (String.valueOf(studentNumber).length() != 10) {
            throw new IllegalArgumentException("10글자의 학번을 입력해주세요.", ErrorField.of("studentNumber", studentNumber));
        }
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentNumber that)) return false;
        return Objects.equals(studentNumber, that.studentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber);
    }

    @Override
    public String toString() {
        return String.valueOf(studentNumber);
    }
}
