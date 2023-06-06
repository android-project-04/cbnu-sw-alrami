package cbnu.io.cbnuswalrami.test.acceptance.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.communitybookmark.CommunityBookmarkFixture;
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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("북마크의 게시물 제거 인수테스트")
class DeleteCommunityBookmarkTest extends AcceptanceTestBase {

    @Autowired
    private CommunityBookmarkFixture communityBookmarkFixture;

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("북마크를 취소한다.")
    @Test
    void given_exist_id_when_delete_return_status_ok() {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);

        ResponseCommunity responseCommunity = communityBookmarkFixture.saveCommunityToBookmark(communityFixture.writeOneCommunity());
        Long deleteId = responseCommunity.getId();

        ExtractableResponse<Response> extract = given(documentationSpec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .filter(document(
                        "delete-favorite-bookmark",
                        getResponseFieldsSnippet()
                )).when()
                .delete("/api/community-bookmark/" + deleteId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(extract.jsonPath().getString("data")).isEqualTo(deleteId + "번 북마크가 잘 삭제되었습니다.");
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("잘 삭제됬다는 메시지를 드립니다.")
        );
    }
}
