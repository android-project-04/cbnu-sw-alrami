package cbnu.io.cbnuswalrami.test.acceptance.community;

import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.community.CommunityFixture;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("취업정보 게시물 커서 페이징 조회(오래된순) 인수테스트")
public class OldEmploymentCommunityCursorPagingTest extends AcceptanceTestBase {

    @Autowired
    private CommunityFixture communityFixture;

    @DisplayName("조회 id인 next 값과 size 6개로 조회하면 status code 200과 오래된 순의 6개의 values 를 리턴한다")
    @Test
    public void given_next_and_size_when__cursor_paging_then_status_code_200() {
        // given
        communityFixture.write15EmploymentCommunity();

        // when
        given(documentationSpec)
                .param("next", 12L)
                .param("size", 6L)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .filter(document(
                        "old-community-paging-get",
                        getRequestParametersSnippet(),
                        getResponseFieldsSnippet()
                ))
                .when()
                .get("/api/employment-community/old/cursor")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all();
    }

    private static RequestParametersSnippet getRequestParametersSnippet() {
        return requestParameters(
                parameterWithName("next").description("해당 커뮤니티 게시물의 id값 부터 검색합니다, null값을 넣을시 최신 회원부터 검색").optional(),
                parameterWithName("size").description("null값을 넣을 시 기본 사이즈 10개입니다, 페이지에서 몇개를 검색할지 설정하는 값").optional()
        );
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 요청할 수 있는 멤버가 있는지"),
                fieldWithPath("data.lastIndex").type(JsonFieldType.NUMBER).description("요청한 멤버의 마지막 index번호(해당 번호를 next값으로 쓰면 된다)"),
                fieldWithPath("data.values[].id").type(JsonFieldType.NUMBER).description("커뮤니티 게시물 id"),
                fieldWithPath("data.values[].title").type(JsonFieldType.STRING).description("게시물 제목"),
                fieldWithPath("data.values[].description").type(JsonFieldType.STRING).description("게시물 내용"),
                fieldWithPath("data.values[].url").type(JsonFieldType.STRING).description("게시물 사진의 url(null 일 수 있음)"),
                fieldWithPath("data.values[].count").type(JsonFieldType.NUMBER).description("게시물 조회수"),
                fieldWithPath("data.values[].createdAt").type(JsonFieldType.STRING).description("게시물 생성 날짜")
        );
    }
}
