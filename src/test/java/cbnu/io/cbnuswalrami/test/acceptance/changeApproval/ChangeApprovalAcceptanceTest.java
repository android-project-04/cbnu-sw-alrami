package cbnu.io.cbnuswalrami.test.acceptance.changeApproval;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.web.user.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.business.web.user.application.SignupCommand;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.MemberFixture;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.baseURI;

@DisplayName("유저 승인기능 인수 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ChangeApprovalAcceptanceTest extends DatabaseTestBase {

    public static final String BASE_URL = "http://localhost";
    @LocalServerPort
    private int port = 8080;

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



    @DisplayName("회원가입한 유저가 있을 떄 어드민 계정은 해당 유저를 승인한다.")
    @Test
    public void given_not_approval_member_when_change_approval_then_ok_member() {
        // given
        Member member = MemberFixture.createMember();

        // 어드민 로그인

        // when - 어드민으로 요청

        // then - Approval이 OK

    }
}
