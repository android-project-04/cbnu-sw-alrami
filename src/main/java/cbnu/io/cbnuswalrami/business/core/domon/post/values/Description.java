package cbnu.io.cbnuswalrami.business.core.domon.post.values;

import cbnu.io.cbnuswalrami.business.core.error.ErrorField;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Description {

    private String description;

    protected Description() {

    }

    private Description(String description) {
        validateDescription(description);
        this.description = description;
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("글을 작성해주세요.", ErrorField.of("description", description));
        }
        if (description.length() > 3000) {
            throw new IllegalArgumentException("게시글을 3000자 이상 넘기지 말아주세요.", ErrorField.of("description", description));
        }
    }

    public static Description from(String description) {
        return new Description(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description that)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
