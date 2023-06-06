package cbnu.io.cbnuswalrami.test.acceptance.favorites.communitybookmark;

import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("북마크 id로 게시글 조회 인수테스트")
class FindCommunityByBookmarkIdTest extends AcceptanceTestBase {

    @Autowired
    private CommunityFixture communityFixture;

    @Autowired
    private CommunityBookmarkFixture communityBookmarkFixture;


    @DisplayName("북마크 id를 통해 게시글을 조회한다.")
    @Test
    void given_bookmark_id_when_find_then_return_response_community() {
        ResponseCommunity responseCommunity = communityFixture.writeOneCommunity();
        ResponseCommunity savedCommunity = communityBookmarkFixture.saveCommunityToBookmark(responseCommunity);

        ExtractableResponse<Response> extract = given(documentationSpec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .filter(document(
                        "get-favorite-community-by-id",
                        getResponseFieldsSnippet()
                ))
                .when()
                .get("/api/community-bookmark/" + savedCommunity.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract();

        long actual1 = extract.jsonPath().getLong("data.id");
        long actual2 = extract.jsonPath().getLong("data.count");
        assertThat(actual1).isEqualTo(responseCommunity.getId());
        assertThat(actual2).isEqualTo(0L);
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
