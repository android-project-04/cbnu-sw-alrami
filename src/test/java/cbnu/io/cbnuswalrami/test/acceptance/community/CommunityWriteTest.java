package cbnu.io.cbnuswalrami.test.acceptance.community;

import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestPartFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestPartsSnippet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("커뮤니티에 글쓰기 인수 테스트")
public class CommunityWriteTest extends AcceptanceTestBase {

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("커뮤니티 글쓰기 API 테스트")
    @Test
    public void given_when_then() {
        // given
        byte[] fileByte = "Hello, World".getBytes();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);

        given(documentationSpec)
                .multiPart("requestCommunity", getRequestCommunity().toString(), APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .multiPart("file", "file.png", fileByte, MULTIPART_FORM_DATA_VALUE)
                .header("Authorization", "Bearer " + token)
                .contentType(MULTIPART_FORM_DATA_VALUE)
                .filter(document(
                        "community-post",
                        getRequestPartsSnippet(),
                        getRequestPartFieldsSnippet(),
                        getResponseFieldsSnippet()
                ))
                .when()
                .post("/api/community")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    private static RequestPartsSnippet getRequestPartsSnippet() {
        return requestParts(
                partWithName("requestCommunity").description("커뮤니티 글"),
                partWithName("file").description("커뮤니티에 넣은 사진(안넣어도 가능하다)").optional()
        );
    }

    private static RequestPartFieldsSnippet getRequestPartFieldsSnippet() {
        return requestPartFields(
                "requestCommunity",
                fieldWithPath("title").type(JsonFieldType.STRING).description("글의 제목"),
                fieldWithPath("description").type(JsonFieldType.STRING).description("글의 내용")
        );
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("커뮤니티 게시글 id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.description").type(JsonFieldType.STRING).description("게시글 내용"),
                fieldWithPath("data.url").type(JsonFieldType.STRING).description("첨부한 사진 url(null 일 수 있음)")
        );
    }

    private JsonObject getRequestCommunity() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", "title");
        jsonObject.addProperty("description", "description");
        return jsonObject;
    }
}
