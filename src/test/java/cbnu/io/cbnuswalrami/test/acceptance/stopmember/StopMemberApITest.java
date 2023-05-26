package cbnu.io.cbnuswalrami.test.acceptance.stopmember;

import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("유저를 30일 정지시키는 API 인수테스트")
class StopMemberApITest extends AcceptanceTestBase {


    @Autowired
    private SignupFixture signupFixture;

    @DisplayName("유저 정지 기능 API를 테스트한다.")
    @Test
    public void given_15_notifications_when_find_5_notifications_then_5_notifications() {
        // given
        Long memberId = signupFixture.signupMember(MemberFixture.createMember()).getId();


        given(documentationSpec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .filter(
                        document(
                                "post-stop-member",
                                getResponseFieldsSnippet()
                        )
                )
                .when().post("/api/admin/stop-member/" + memberId)
                .then().statusCode(HttpStatus.OK.value());
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("정지당한 유저의 id값"),
                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("정지당한 유저의 닉네임"),
                fieldWithPath("data.role").type(JsonFieldType.STRING).description("정지당한 유저의 바뀐 권한"),
                fieldWithPath("data.lastStopDay").type(JsonFieldType.STRING).description("정지당한 유저가 정지가 풀리는 날짜")
        );
    }
}
