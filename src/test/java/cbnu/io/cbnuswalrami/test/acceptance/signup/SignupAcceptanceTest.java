package cbnu.io.cbnuswalrami.test.acceptance.signup;

import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.test.helper.fake.s3.S3Configuration;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestPartFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestPartsSnippet;

import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("회원가입 기능 인수 테스트")
@Import(S3Configuration.class)
public class SignupAcceptanceTest extends AcceptanceTestBase {


    @Test
    @DisplayName("회원가입 API 테스트")
    public void given_valid_signup_info_when_signup_then_id() {
        // given - loginId, password, studentNumber
        JsonObject jsonObject = getSignupMember();
        byte[] fileByte = "Hello, World".getBytes();

        RequestSpecification signup = RestAssured.given(documentationSpec)
                .multiPart("request", jsonObject.toString(), APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .multiPart("file", "file.png", fileByte, MULTIPART_FORM_DATA_VALUE)
                .contentType(MULTIPART_FORM_DATA_VALUE)
                .filter(document(
                        "users-post",
                        getRequestPartsSnippet(),
                        getRequestPartFieldsSnippet(),
                        getResponseFieldsSnippet()
                ));

        // then - signup
        signup.when()
                .post("/api/member/signup")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private RequestPartsSnippet getRequestPartsSnippet() {
        return requestParts(
                partWithName("file").description("학생증 사진"),
                partWithName("request").description("유저 정보")
        );
    }

    private RequestPartFieldsSnippet getRequestPartFieldsSnippet() {
        return requestPartFields(
                "request",
                fieldWithPath("loginId").description("로그인 아이디(8글자 이상의 영어)"),
                fieldWithPath("password").description("패스워드(최소 8자리 이상, 대/소문자, 숫자 및 특수문자 각 1개 이상 포함)"),
                fieldWithPath("studentNumber").description("학번"),
                fieldWithPath("nickname").description("닉네임(2글자 이상의 영어 혹은 한글, 숫자)")
        );
    }

    private ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("유저 id값")
        );
    }

    private static JsonObject getSignupMember() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loginId", "abcd1234");
        jsonObject.addProperty("password", "Abcd1234@!");
        jsonObject.addProperty("nickname", "hero123");
        jsonObject.addProperty("studentNumber", 2020110110);
        return jsonObject;
    }
}
