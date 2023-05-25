package cbnu.io.cbnuswalrami.test.integration.community;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType;
import cbnu.io.cbnuswalrami.business.web.community.application.service.CommunityWriteService;
import cbnu.io.cbnuswalrami.business.web.community.presentation.request.RequestCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.file.FileFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import static cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("커뮤니티에 글쓰기 통합 테스트")
public class CommunityWriteTest extends DatabaseTestBase {

    @Autowired
    private CommunityWriteService communityWriteService;

    @Autowired
    private MemberFindUtil memberFindUtil;

    @DisplayName("커뮤니티에 글을 저장하면 저장한 글을 반환한다.")
    @Test
    public void when_write_community_then_return_community_response() {
        // given
        Member member = memberFindUtil.findMemberByAuthentication();
        MultipartFile file = FileFixture.createFile();
        String title = "title";
        String description = "sdfsdfdsf";
        RequestCommunity requestCommunity = new RequestCommunity(title, description);

        // when
        ResponseCommunity actual = communityWriteService.writeCommunity(requestCommunity, file, member, COMMUNITY);

        // then
        assertThat(actual.getTitle()).isEqualTo(title);
        assertThat(actual.getDescription()).isEqualTo(description);
        assertThat(actual.getId()).isNotNull();
    }

    @DisplayName("제목을 입력하지 않으면 예외를 던진다.")
    @Test
    public void given_empty_title_when_write_community_then_ex() {
        // given
        Member member = memberFindUtil.findMemberByAuthentication();
        MultipartFile file = FileFixture.createFile();
        String title = "";
        String description = "sdfsdfdsf";
        RequestCommunity requestCommunity = new RequestCommunity(title, description);

        // then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> communityWriteService.writeCommunity(
                                requestCommunity,
                                file,
                                member,
                                COMMUNITY
                        )
                );
        assertThat(exception.getMessage()).isEqualTo("제목을 입력해주세요.");
    }

    @DisplayName("제목이 null 값이면 예외를 던진다.")
    @Test
    public void given_null_title_when_write_community_then_ex() {
        // given
        Member member = memberFindUtil.findMemberByAuthentication();
        MultipartFile file = FileFixture.createFile();
        String title = null;
        String description = "sdfsdfdsf";
        RequestCommunity requestCommunity = new RequestCommunity(title, description);

        // then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> communityWriteService.writeCommunity(
                                requestCommunity,
                                file,
                                member,
                                COMMUNITY
                        )
                );
        assertThat(exception.getMessage()).isEqualTo("제목을 입력해주세요.");
    }

    @DisplayName("글을 입력하지 않으면 예외를 던진다.")
    @Test
    public void given_empty_desc_when_write_community_then_ex() {
        // given
        Member member = memberFindUtil.findMemberByAuthentication();
        MultipartFile file = FileFixture.createFile();
        String title = "title";
        String description = "";
        RequestCommunity requestCommunity = new RequestCommunity(title, description);

        // then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> communityWriteService.writeCommunity(
                                requestCommunity,
                                file,
                                member,
                                COMMUNITY
                        )
                );
        assertThat(exception.getMessage()).isEqualTo("글을 작성해주세요.");

    }

    @DisplayName("글이 null 값이면 예외를 던진다.")
    @Test
    public void given_null_desc_when_write_community_then_ex() {
        // given
        Member member = memberFindUtil.findMemberByAuthentication();
        MultipartFile file = FileFixture.createFile();
        String title = "title";
        String description = null;
        RequestCommunity requestCommunity = new RequestCommunity(title, description);

        // then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> communityWriteService.writeCommunity(
                                requestCommunity,
                                file,
                                member,
                                COMMUNITY
                        )
                );
        assertThat(exception.getMessage()).isEqualTo("글을 작성해주세요.");
    }
}
