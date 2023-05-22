package cbnu.io.cbnuswalrami.test.unit;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.Description;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.Title;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.post.PostFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("커뮤니티 단위 테스트")
public class CommunityTest {

    @DisplayName("커뮤니티 글을 삭제하면 삭제값이 TRUE가 된다.")
    @Test
    public void given_community_when_change_delete_then_true() {
        // given
        Community community = createCommunity();
        // when
        community.changeToDeleteTrue();

        // then
        assertThat(community.getIsDeleted()).isEqualTo(Deleted.TRUE);
    }

    private Community createCommunity() {
        return new Community(
                Title.from("title"),
                Description.from("description"),
                "www.ac.kr",
                MemberFixture.createMember()
        );
    }

    @DisplayName("게시글의 제목이 null이거나 비어있다면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    public void when_title_is_null_or_empty_then_illegalargument_exception_should_be_happen(String parameter) {
        assertThatThrownBy(() -> Title.from(parameter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목을 입력해주세요.");
    }

    @DisplayName("1000글자를 넘어가는 제목은 IllegalArgumentException이 발생한다.")
    @Test
    public void given_lager_then_1000_title_when_create_then_illegalargument_exception_should_be_happen() {
        assertThatThrownBy(() -> Title.from(PostFixture.createLengthLagerThen1000String()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목을 1000글자 이상 넘기지 말아주세요.");
    }

    @DisplayName("게시글의 내용이 null이거나 비어있다면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    public void when_description_is_null_or_empty_then_illegalargument_exception_should_be_happen(String parameter) {
        assertThatThrownBy(() -> Description.from(parameter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("글을 작성해주세요.");
    }

    @DisplayName("1000글자를 넘어가는 제목은 IllegalArgumentException이 발생한다.")
    @Test
    public void given_lager_then_2000_description_when_create_then_illegalargument_exception_should_be_happen() {
        assertThatThrownBy(() -> Description.from(PostFixture.createLengthLagerThen3000String()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게시글을 3000자 이상 넘기지 말아주세요.");
    }
}
