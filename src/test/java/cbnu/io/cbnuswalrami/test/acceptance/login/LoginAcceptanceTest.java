package cbnu.io.cbnuswalrami.test.acceptance.login;

import cbnu.io.cbnuswalrami.business.common.filter.SecurityFilter;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.web.user.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.SignupFixture;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.ResponseHeadersSnippet;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static cbnu.io.cbnuswalrami.test.helper.util.ApiDocumentUtils.*;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("로그인 기능 인수 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class LoginAcceptanceTest extends DatabaseTestBase {

    public static final String BASE_URL = "http://localhost";
    @LocalServerPort
    private int port = 8080;

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private ApprovalChangeCommand approvalChangeCommand;

    private RequestSpecification documentationSpec = new RequestSpecBuilder()
            .setPort(port)
            .setBaseUri(BASE_URL)
            .build();

    @BeforeAll
    public void configureRestAssured() {
        RestAssured.port = port;
        baseURI = BASE_URL;
    }


    @BeforeEach
    public void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }


    @DisplayName("로그인 API 테스트")
    @Test
    public void given_when_then() {
        // given - loginId, password
        String loginId = "abcd1234";
        String password = "Abcd1234@!";
        String nickname = "히어로123";
        JsonObject jsonObject = getLoginMember(loginId, password);

        // 회원가입r
        Member member = signupFixture.signupMember(loginId, password, nickname);

        // 승인된 멤버로 전환
        approvalChangeCommand.changeApproval(member.getId());

        // when - login
        given(documentationSpec)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .filter(document(
                        "login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getRequestFieldsSnippet(),
                        getResponseHeadersSnippet()
                ))
                .when().post("/api/member/login").then().statusCode(HttpStatus.OK.value());
    }

    private static JsonObject getLoginMember(String loginId, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loginId", loginId);
        jsonObject.addProperty("password", password);
        return jsonObject;
    }

    private ResponseHeadersSnippet getResponseHeadersSnippet() {
        return responseHeaders(
                headerWithName(SecurityFilter.AUTHORIZATION_HEADER).description("Access token"),
                headerWithName(SecurityFilter.SESSION_ID).description("Refresh token"),
                headerWithName(SecurityFilter.AUTHORITY).description("멤버 권한")
        );
    }

    private RequestFieldsSnippet getRequestFieldsSnippet() {
        return requestFields(
                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디(8자리 이상)"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드(최소 8자리 이상, 대/소문자, 숫자 및 특수문자 각 1개 이상 포함)")
        );
    }
}
