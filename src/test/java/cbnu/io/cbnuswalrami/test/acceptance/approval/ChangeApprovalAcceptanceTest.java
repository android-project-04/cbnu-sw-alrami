package cbnu.io.cbnuswalrami.test.acceptance.approval;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("유저 승인기능 인수 테스트")
public class ChangeApprovalAcceptanceTest extends AcceptanceTestBase {

    @Autowired
    private SignupFixture signupFixture;

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
