package cbnu.io.cbnuswalrami.test.integration.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.StudentNumber;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.member.application.service.ChangeNicknameService;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.MemberDto;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("멤버 닉네임 변경 통합테스트")
@ExtendWith(MockitoExtension.class)
class ChangeNicknameTest extends DatabaseTestBase {

    @InjectMocks
    private ChangeNicknameService changeNicknameService;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private Member member;

    @DisplayName("31일 지난 멤버의 닉네임을 변경할 수 있다.")
    @Test
    void given_valid_change_day_when_change_nickname_then_changed_member() {
        // Prepare data
        String newNickname = "newNickname";
        LocalDateTime lastModifiedDate = LocalDateTime.now().minusDays(31);

        when(member.getLastModifiedAt()).thenReturn(lastModifiedDate);
        when(member.getId()).thenReturn(1L);
        when(member.getStudentNumber()).thenReturn(StudentNumber.from(2020110110));

        // Execute
        MemberDto memberDto = changeNicknameService.changeNickname(newNickname, member);

        // Verify
        verify(member, times(1)).changeNickname(newNickname);
        assertEquals(newNickname, memberDto.getNickname());
    }


    @DisplayName("30일 이상 지나지 않은 멤버의 닉네임을 변경할 수 없다.")
    @Test
    void invalid_change_day_when_change_nickname_when_illegalargument_exception() {
        // Prepare data
        String newNickname = "newNickname";
        LocalDateTime lastModifiedDate = LocalDateTime.now().minus(29, ChronoUnit.DAYS);

        when(member.getLastModifiedAt()).thenReturn(lastModifiedDate);

        // Execute and assert exception
        assertThrows(IllegalArgumentException.class, () -> changeNicknameService.changeNickname(newNickname, member));
    }
}
