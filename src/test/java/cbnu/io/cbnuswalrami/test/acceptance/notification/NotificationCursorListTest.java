package cbnu.io.cbnuswalrami.test.acceptance.notification;

import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;

import static io.restassured.RestAssured.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("공지사항 리스트 조회 API 인수 테스트")
public class NotificationCursorListTest extends AcceptanceTestBase {

        @Autowired
        private NotificationFixture notificationFixture;

        @DisplayName("공지사항 리스트를 조회한다")
        @Test
        public void given_15_notifications_when_find_5_notifications_then_5_notifications() {
                // given
                notificationFixture.save15RandomNotification();
                given(documentationSpec)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .param("next", 6L)
                        .param("size", 5L)
                        .filter(
                                document(
                                        "get-notifications",
                                        getRequestParametersSnippet(),
                                        getResponseFieldsSnippet()
                                )
                        )
                        .when().get("/api/notification/list")
                        .then().statusCode(HttpStatus.OK.value());
        }

        private static ResponseFieldsSnippet getResponseFieldsSnippet() {
                return responseFields(
                        fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                        fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                        fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 요청할 수 있는 멤버가 있는지"),
                        fieldWithPath("data.lastIndex").type(JsonFieldType.NUMBER).description("요청한 멤버의 마지막 index번호, 해당 Index로 다음 커서를 요청하면 된다."),
                        fieldWithPath("data.values[].id").type(JsonFieldType.NUMBER).description("공지 id"),
                        fieldWithPath("data.values[].title").type(JsonFieldType.STRING).description("공지 제목"),
                        fieldWithPath("data.values[].url").type(JsonFieldType.STRING).description("공지 url")
                );
        }

        private static RequestParametersSnippet getRequestParametersSnippet() {
                return requestParameters(
                        parameterWithName("size").description("null값을 넣을 시 기본 사이즈 10개입니다, 페이지에서 몇개를 검색할지 설정하는 값").optional(),
                        parameterWithName("next").description("해당 회원의 id값 부터 검색합니다, null값을 넣을시 최신 회원부터 검색").optional()
                );
        }
}
