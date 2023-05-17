package cbnu.io.cbnuswalrami.test.acceptance.approval;

import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.SignupFixture;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@DisplayName("비승인 유저 커서 페이징 인수 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class NoApprovalCursorPagingTest extends DatabaseTestBase {

    public static final String BASE_URL = "http://localhost";

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private SignupFixture signupFixture;


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

    @DisplayName("비승인 유저들을 커서 페이징으로 조회")
    @Test
    public void given_no_approval_list_when_cursor_paging_then_cursor_result() {
        // given
        signupFixture.signup15NonApprovalMembers();

        given(documentationSpec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .param("size", 10L)
                .param("next", 12L)
                .filter(
                        document(
                                "NoApprovalMembers",
                                requestParameters(
                                        parameterWithName("size").description("null값을 넣을 시 기본 사이즈 10개입니다, 페이지에서 몇개를 검색할지 설정하는 값"),
                                        parameterWithName("next").description("해당 회원의 id값 부터 검색합니다, null값을 넣을시 최신 회원부터 검색")
                                ),
                                responseFields(
                                        fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                                        fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 요청할 수 있는 멤버가 있는지"),
                                        fieldWithPath("data.lastIndex").type(JsonFieldType.NUMBER).description("요청한 멤버의 마지막 index번호"),
                                        fieldWithPath("data.values[].id").type(JsonFieldType.NUMBER).description("멤버 id"),
                                        fieldWithPath("data.values[].nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.values[].studentNumber").type(JsonFieldType.NUMBER).description("학번")
                                )
                        )
                )
                .when().get("/api/admin/no/approval/members")
                .then()
                .statusCode(HttpStatus.OK.value()).log().all();
    }
}
