package cbnu.io.cbnuswalrami.test.integration.community;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.application.service.paging.CommunityCursorPagingService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("취업 커뮤니티 최신순 커서 페이징 조회 통합테스트")
public class EmploymentCommunityCursorPagingTest extends DatabaseTestBase {

    @Autowired
    private CommunityCursorPagingService communityCursorPagingService;

    @Autowired
    private CommunityFixture communityFixture;

    @DisplayName("15개가 있을 때 10번, 5번 조회하면 15개를 조회 할 수 있다")
    @Test
    public void given_15_employment_community_when_find_then_find_15_size() {
        // given
        communityFixture.write15EmploymentCommunity();

        // when
        CursorResult cursorResult1 = communityCursorPagingService.findEmploymentCommunity(Cursor.from(null, null));
        CursorResult cursorResult2 = communityCursorPagingService.findEmploymentCommunity(Cursor.from(cursorResult1.getLastIndex(), null));

        // then
        assertThat(cursorResult1.getValues().size()).isEqualTo(10);
        assertThat(cursorResult2.getValues().size()).isEqualTo(5);
    }

    @DisplayName("next 값 7 size 5이면 hastNext값이 true이다")
    @Test
    public void given_next_7_when_find_by_cursor_then_hast_next_true() {
        // given
        communityFixture.write15EmploymentCommunity();

        // when
        CursorResult actual = communityCursorPagingService.findEmploymentCommunity(Cursor.from(7L, 5L));

        // then
        assertThat(actual.getHasNext()).isEqualTo(true);
    }

    @DisplayName("next 값이 6 size가 5 이면 hastNext값이 false이다")
    @Test
    public void given_next_6_when_find_by_cursor_then_hast_next_false() {
        // given
        communityFixture.write15EmploymentCommunity();

        // when
        CursorResult actual = communityCursorPagingService.findEmploymentCommunity(Cursor.from(6L, 5L));
        System.out.println(actual.getValues());

        // then
        assertThat(actual.getHasNext()).isEqualTo(false);
    }

    @DisplayName("next 값이 4 size 가 5 이면 3개를 반환한다.")
    @Test
    public void given_next_4_and_size_5_when_find_by_cursor_then_size_3() {
        // given
        communityFixture.write15EmploymentCommunity();

        // when
        CursorResult actual = communityCursorPagingService.findEmploymentCommunity(Cursor.from(4L, 5L));

        // then
        assertThat(actual.getValues().size()).isEqualTo(3L);
    }
}
