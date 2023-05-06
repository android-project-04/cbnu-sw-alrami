package cbnu.io.cbnuswalrami.test.acceptance.login;

import cbnu.io.cbnuswalrami.business.common.filter.SecurityFilter;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.web.user.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.business.web.user.application.LoginCommand;
import cbnu.io.cbnuswalrami.business.web.user.application.SignupCommand;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.SignupRequest;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;
import static org.assertj.core.api.Assertions.assertThat;
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
    private LoginCommand loginCommand;

    @Autowired
    private SignupCommand signupCommand;

    @Autowired
    private ApprovalChangeCommand approvalChangeCommand;

    private RequestSpecification documentationSpec = new RequestSpecBuilder().setPort(port).setBaseUri(BASE_URL).build();

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
        byte[] bytes = "Hello, World".getBytes();
        SignupRequest signupRequest = new SignupRequest(loginId, password, 2020110110);
        MockMultipartFile file = new MockMultipartFile("file", "file.png", MediaType.IMAGE_PNG_VALUE, bytes);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loginId", loginId);
        jsonObject.addProperty("password", password);

        // 회원가입
        Member signupMember = signupCommand.signup(signupRequest, file);

        // 승인된 멤버로 전환
        approvalChangeCommand.changeApproval(signupMember.getId());

        // when - login
        given(documentationSpec)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .filter(document(
                        "login",
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
                        ),
                        responseHeaders(
                                headerWithName(SecurityFilter.AUTHORIZATION_HEADER).description("Access token"),
                                headerWithName(SecurityFilter.SESSION_ID).description("Refresh token"),
                                headerWithName(SecurityFilter.AUTHORITY).description("멤버 권한")
                        )
                ))
                .when().post("/api/member/login").then().statusCode(HttpStatus.OK.value());
    }
}
