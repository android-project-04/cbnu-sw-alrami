package cbnu.io.cbnuswalrami.test.acceptance.favorites.notificationbookmark;

import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("공지글 즐겨찾기에 저장 API 인수테스트")
class SaveNotificationBookmarkTest extends AcceptanceTestBase {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private NotificationFixture notificationFixture;


    @DisplayName("공지글이 있을 때 즐겨찾기에 저장하면 status code 200이 응답된다.")
    @Test
    void given_notification_when_save_in_favorite_then_status_ok() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);

        List<Notification> notifications = notificationFixture.save15RandomNotification();
        Long saveNotificationId = notifications.get(0).getId();

        given(documentationSpec)
                .header("authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filter(document(
                        "post-favorite-notification",
                        getResponseFieldsSnippet()

                ))
                .when()
                .post("/api/notification-bookmark/" + saveNotificationId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all();

    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("즐겨찾기에 저장한 공지 id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("즐겨찾기에 저장한 공지 제목"),
                fieldWithPath("data.url").type(JsonFieldType.STRING).description("즐겨찾기에 저장한 공지 url")
        );
    }
}
