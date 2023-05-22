package cbnu.io.cbnuswalrami.test.unit.post;

import cbnu.io.cbnuswalrami.business.core.domon.post.values.Title;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("게시글 제목 단위 테스트")
public class TitleTest {

    @DisplayName("유효한 제목은 Title 객체를 반환한다.")
    @Test
    void from_valid_title_returns_title() {
        // Arrange
        String validTitle = "A valid title";

        // Act
        Title title = Title.from(validTitle);

        // Assert
        assertNotNull(title);
        assertEquals(validTitle, title.getTitle());
    }

    @DisplayName("다른 제목을 가진 Title 객체는 동일하지 않다.")
    @Test
    void equals_different_title_returns_false() {
        String title1 = "Title1";
        String title2 = "Title2";

        Title titleObj1 = Title.from(title1);
        Title titleObj2 = Title.from(title2);

        assertNotEquals(titleObj1, titleObj2);
        assertFalse(titleObj1.equals(titleObj2));
    }


    @DisplayName("같은 제목을 가진 Title 객체는 동일하다.")
    @Test
    void equals_same_title_returns_true() {
        // Arrange
        String title1 = "Title";
        String title2 = "Title";

        // Act
        Title titleObj1 = Title.from(title1);
        Title titleObj2 = Title.from(title2);

        // Assert
        assertEquals(titleObj1, titleObj2);
        assertTrue(titleObj1.equals(titleObj2));
    }

    @DisplayName("같은 제목을 가진 Title 객체의 해시코드는 같다.")
    @Test
    void hash_code_same_title_returns_same_hash_code() {
        String title1 = "Title";
        String title2 = "Title";

        Title titleObj1 = Title.from(title1);
        Title titleObj2 = Title.from(title2);

        assertEquals(titleObj1.hashCode(), titleObj2.hashCode());
    }

    @DisplayName("다른 제목을 가진 Title 객체의 해시코드는 다르다.")
    @Test
    void hash_code_different_title_returns_different_hash_code() {
        String title1 = "Title1";
        String title2 = "Title2";

        Title titleObj1 = Title.from(title1);
        Title titleObj2 = Title.from(title2);

        assertNotEquals(titleObj1.hashCode(), titleObj2.hashCode());
    }
}
