package cbnu.io.cbnuswalrami.test.acceptance.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
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
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("게시글을 즐겨찾기로 저장 인수테스트")
public class SaveCommunityBookmarkTest extends AcceptanceTestBase {

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private MemberFindUtil memberFindUtil;

    @Autowired
    private TokenProvider tokenProvider;


    @DisplayName("게시긇 하나를 즐겨찾기로 저장한다.")
    @Test
    void given_community_when_save_to_bookmark_then_ok() {
        // given
        Member member = memberFindUtil.findMemberByAuthentication();
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community(member);
        Long saveId = responseCommunities.get(0).getId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String token = tokenProvider.createToken(authentication);

        given(documentationSpec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .filter(document(
                        "post-community-favorite",
                        getResponseFieldsSnippet()
                ))
                .when()
                .post("/api/community-bookmark/" + saveId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("즐겨찾기에 저장한 커뮤니티 id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("즐겨찾기에 저장한 커뮤니티 제목"),
                fieldWithPath("data.description").type(JsonFieldType.STRING).description("즐겨찾기에 저장한 커뮤니티 게시글"),
                fieldWithPath("data.url").type(JsonFieldType.STRING).description("즐겨찾기에 저장한 커뮤니티 사진 url"),
                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("즐겨찾기에 저장한 커뮤니티 생성일자"),
                fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("즐겨찾기에 저장한 커뮤니티 게시물 조회수")
        );
    }
}
