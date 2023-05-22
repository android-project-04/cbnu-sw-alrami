package cbnu.io.cbnuswalrami.business.core.domon.post.values;

import cbnu.io.cbnuswalrami.business.core.error.ErrorField;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Title {

    private String title;

    protected Title () {

    }

    private Title(String title) {
        validateTitle(title);
        this.title = title;
    }

    public static Title from(String title) {
        return new Title(title);
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목을 입력해주세요.", ErrorField.of("title", title));
        }

        if (title.length() > 1000) {
            throw new IllegalArgumentException("제목을 1000글자 이상 넘기지 말아주세요.", ErrorField.of("title", title));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Title title1)) return false;
        return Objects.equals(title, title1.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
