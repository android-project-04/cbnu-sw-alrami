package cbnu.io.cbnuswalrami.test.acceptance.approval;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static cbnu.io.cbnuswalrami.test.helper.util.ApiDocumentUtils.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("유저 승인기능 인수 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ChangeApprovalAcceptanceTest extends DatabaseTestBase {

    public static final String BASE_URL = "http://localhost";
    @LocalServerPort
    private int port = 8080;

    @Autowired
    private SignupFixture signupFixture;


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


    @DisplayName("회원가입한 유저가 있을 떄 어드민 계정은 해당 유저를 승인한다.")
    @Test
    public void given_not_approval_member_when_change_approval_then_ok_member() {
        // given
        Member member = MemberFixture.createMember();
        Member signupMember = signupFixture.signupNonApprovalMember(
                member.getLoginId().getLoginId(),
                "SjSj1234!@",
                member.getNickname().getNickname()
        );

        String uri = "/api/admin/approval/" + signupMember.getId();

        // when - 어드민으로 요청
        given(documentationSpec)
                .contentType(ContentType.JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .filter(document(
                                "change-approval",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                getResponseFieldsSnippet()
                        )
                )
                .when()
                .put(uri)
                .then().statusCode(HttpStatus.OK.value());
    }

    private ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("승인된 유저 id값")
        );
    }
}
