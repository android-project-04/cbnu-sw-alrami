package cbnu.io.cbnuswalrami.test.integration.util;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Authentication 통합 테스트")
public class MemberFindUtilTest extends DatabaseTestBase {

    @Autowired
    private MemberFindUtil memberFindUtil;

    @DisplayName("Authentication 에서 유저 정보를 가져오는지 테스트한다.")
    @Test
    public void given_when_then() {
        // when
        Member member = memberFindUtil.findMemberByAuthentication();

        // then
        assertThat(member.getId()).isNotNull();
    }
}
