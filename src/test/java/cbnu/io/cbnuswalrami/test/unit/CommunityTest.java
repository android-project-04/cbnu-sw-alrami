package cbnu.io.cbnuswalrami.test.unit;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.community.entity.Community;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
                "title",
                "description",
                "www.ac.kr",
                MemberFixture.createMember()
        );
    }

}
