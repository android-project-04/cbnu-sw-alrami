package cbnu.io.cbnuswalrami.test.helper.fixture.post;

import cbnu.io.cbnuswalrami.business.core.domon.post.values.Title;

public final class PostFixture {

    private PostFixture() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static String createLengthLagerThen1000String() {
        return "A".repeat(1001);
    }

    public static String createLengthLagerThen3000String() {
        return "A".repeat(3001);
    }

    public static Title createValidPostTitle() {
        return Title.from("testTitle");
    }
}
