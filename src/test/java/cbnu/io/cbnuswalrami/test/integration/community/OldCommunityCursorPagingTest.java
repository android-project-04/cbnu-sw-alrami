package cbnu.io.cbnuswalrami.test.integration.community;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.application.service.paging.OldCommunityCursorPagingService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("오래된 순으로 커뮤니티 게시물 커서 페이징 통합 테스트")
public class OldCommunityCursorPagingTest extends DatabaseTestBase {

    @Autowired
    private OldCommunityCursorPagingService oldCommunityCursorPagingService;

    @Autowired
    private CommunityFixture communityFixture;

    @DisplayName("15개의 데이터가 저장 되있을 떄 next=null size1을 가져오면  size는 1이다.")
    @ParameterizedTest
    @ValueSource(longs = 1)
    public void given_data_15_when_size_1_then_size_1(Long size) {
        communityFixture.write15Community();
        CursorResult cursorResult = oldCommunityCursorPagingService.findByCursor(Cursor.from(null, size));
        int actual = cursorResult.getValues().size();
        assertThat(actual).isEqualTo(1);
    }

    @DisplayName("데이터가 15개일 때 10개, 5개 씩 가져올 수 있다.")
    @Test
    public void given_data_size_15_when_find_then_ok() {
        communityFixture.write15Community();
        CursorResult cursorResult1 = oldCommunityCursorPagingService.findByCursor(Cursor.from(null, null));
        CursorResult cursorResult2 = oldCommunityCursorPagingService
                .findByCursor(Cursor.from(cursorResult1.getLastIndex(), null));

        assertThat(cursorResult1.getValues().size()).isEqualTo(10);
        assertThat(cursorResult2.getValues().size()).isEqualTo(5);

    }

    @DisplayName("데이터가 15개일 때 next값이 5이면 hasNext 값이 false이다.")
    @Test
    public void given_data_size_15_when_find_then_has_next_false() {
        // given
        communityFixture.write15Community();

        // when
        CursorResult cursorResult = oldCommunityCursorPagingService.findByCursor(Cursor.from(5L, null));

        // then
        assertThat(cursorResult.getHasNext()).isEqualTo(false);
    }

    @DisplayName("데이터가 15개일 때 next값이 4이면 hasNext값이 true이다.")
    @Test
    public void given_data_size_15_when_find_then_has_next_true() {
        // given
        communityFixture.write15Community();

        // when
        CursorResult cursorResult = oldCommunityCursorPagingService.findByCursor(Cursor.from(4L, null));

        // then
        assertThat(cursorResult.getHasNext()).isEqualTo(true);
    }

    @DisplayName("15개의 데이터에서 3개를 가져오면 lastIndex 값이 3이다.")
    @ParameterizedTest
    @ValueSource(longs = 3)
    void given_data_size_15_when_find_then_last_index_12(Long size) {
        communityFixture.write15Community();
        CursorResult cursorResult = oldCommunityCursorPagingService.findByCursor(Cursor.from(null, size));

        assertThat(cursorResult.getLastIndex()).isEqualTo(3L);
    }

    @DisplayName("데이터의 크기보다 큰 next값을 주게되면 빈 배열을 반환하며 lastIndex값은 0이다.")
    @Test
    void given_data_size_15_when_find_next_16_then_ex() {
        communityFixture.write15Community();
        CursorResult cursorResult = oldCommunityCursorPagingService.findByCursor(Cursor.from(16L, 10L));

        assertThat(cursorResult.getValues().size()).isEqualTo(0);
        assertThat(cursorResult.getLastIndex()).isEqualTo(0L);
    }
}
