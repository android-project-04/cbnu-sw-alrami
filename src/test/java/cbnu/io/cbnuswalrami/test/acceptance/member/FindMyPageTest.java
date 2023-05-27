package cbnu.io.cbnuswalrami.test.acceptance.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("마이페이지 조회 API 인수테스트")
public class FindMyPageTest extends AcceptanceTestBase {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberFindUtil memberFindUtil;

    @Autowired
    private CommunityFixture communityFixture;

    @DisplayName("마이페이지를 조회한다.")
    @Test
    void when_find_my_page__then_return_status_ok() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);
        Member member = memberFindUtil.findMemberByAuthentication();
        communityFixture.write15Community(member);

        given(documentationSpec)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .filter(document(
                        "get-mypage",
                        responseFields(
                                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("회원의 닉네임"),
                                fieldWithPath("data.studentNumber").type(JsonFieldType.NUMBER).description("회원의 학번"),
                                fieldWithPath("data.responseMyPageCommunities[].communityId").type(JsonFieldType.NUMBER).description("내가쓴 커뮤니티의 글 id"),
                                fieldWithPath("data.responseMyPageCommunities[].title").type(JsonFieldType.STRING).description("내가쓴 커뮤니티의 글 제목")
                        )
                ))
                .when()
                .get("/api/member/mypage")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
