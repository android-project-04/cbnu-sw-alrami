package cbnu.io.cbnuswalrami.test.integration.approval;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.user.application.service.paging.ApprovalCursorPagingService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.SignupFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("비승인 유저 커서 페이지 통합 테스트")
public class NoApprovalCursorPagingTest extends DatabaseTestBase {

    @Autowired
    private ApprovalCursorPagingService approvalCursorPagingService;

    @Autowired
    private SignupFixture signupFixture;


    @DisplayName("index id와 size를 가지고 커서 페이징을 사용하면 다음 아이디와 데이터를 담은 데이터를 반환한다.")
    @Test
    public void given_page_size_next_id_when_cursor_paging_then_cursor_result() {
        // given
        List<Member> members = signupFixture.signup15NonApprovalMembers();

        // when
        CursorResult memberDtosByCursor = approvalCursorPagingService
                .findMemberDtosByCursor(Cursor.from(null, 10L));

        CursorResult memberDtosByCursor2 = approvalCursorPagingService
                .findMemberDtosByCursor(Cursor.from(memberDtosByCursor.getLastIndex(), 10L));

        // then
        assertThat(memberDtosByCursor.getValues().size()).isEqualTo(10L);
        assertThat(memberDtosByCursor2.getValues().size()).isEqualTo(5L);
    }

    @DisplayName("데이터가 없을 때는 커서의 데이터값에 빈 배열이 들어온다.")
    @Test
    public void given_empty_member_when_cursor_paging_then_empty_value() {
        // given

        // when
        CursorResult memberDtosByCursor = approvalCursorPagingService
                .findMemberDtosByCursor(Cursor.from(null, 10L));

        // then
        assertThat(memberDtosByCursor.getValues().size()).isEqualTo(0L);

    }


    @DisplayName("cursor의 id값을 음수로 주면 예외를 던진다.")
    @Test
    public void given_minus_id_when_cursor_paging_then_ex() {
        // given
        List<Member> members = signupFixture.signup15NonApprovalMembers();

        // then
        assertThatThrownBy(() -> approvalCursorPagingService
                .findMemberDtosByCursor(Cursor.from(-1L,  10L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
