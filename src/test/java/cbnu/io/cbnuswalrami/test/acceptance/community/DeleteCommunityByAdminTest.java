package cbnu.io.cbnuswalrami.test.acceptance.community;

import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.LoginFixture;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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

@DisplayName("어드민의 게시물 삭제 인수 테스트")
class DeleteCommunityByAdminTest extends AcceptanceTestBase {


    @Autowired
    private LoginFixture loginFixture;

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("어드민 유저는 게시물을 삭제할 수 있다.")
    @Test
    void given_admin_when_delete_community_then_status_ok() {
        List<ResponseCommunity> responseCommunities = communityFixture.write15Community();
        ResponseCommunity responseCommunity = responseCommunities.get(1);
        Long deleteId = responseCommunity.getId();
        String title = responseCommunity.getTitle();
        loginFixture.LoginAdmin();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);

        loginFixture.LoginAdmin();
        ExtractableResponse<Response> extract = given(documentationSpec)
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .filter(document(
                        "delete-community-by-admin",
                        getResponseFieldsSnippet()
                ))
                .when()
                .delete("/api/admin/community/" + deleteId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        extract.jsonPath().getString("data").equals(title + "을 삭제하였습니다.");
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("게시물 삭제 메시지")
        );
    }
}
