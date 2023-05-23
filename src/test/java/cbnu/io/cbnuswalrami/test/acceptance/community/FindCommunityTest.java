package cbnu.io.cbnuswalrami.test.acceptance.community;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.core.Authentication;


import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("커뮤니티 게시글 하나 조회 인수테스트")
public class FindCommunityTest extends AcceptanceTestBase {

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("커뮤니티 게시글 하나를 id값으로 조회한다.")
    @Test
    void given_id_when_find_community_then_return_status_ok() {
        // given
        Long id = communityFixture.write15Community().get(0).getId();
        Member member = signupFixture.signupMember(MemberFixture.createMember());
        Authentication authentication = tokenProvider.authenticate(member.getId());
        String token = tokenProvider.createToken(authentication);


        given(documentationSpec)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .filter(document(
                        "find-community-by-id",
                        getResponseFieldsSnippet()
                ))
                .when()
                .get("/api/community/" + id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("커뮤니티 게시물 id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                fieldWithPath("data.description").type(JsonFieldType.STRING).description("게시물 내용"),
                fieldWithPath("data.url").type(JsonFieldType.STRING).description("게시물 사진의 url(null 일 수 있음)"),
                fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("게시물 조회수"),
                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시물 생성 날짜")
        );
    }
}
