package cbnu.io.cbnuswalrami.test.acceptance.favorites.notificationbookmark;

import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.NotificationBookmark;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.notificationbookmark.NotificationBookmarkFixture;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("즐겨찾기한 공지사항 즐겨찾기 취소 인수테스트")
class DeleteNotificationBookmarkTest extends AcceptanceTestBase {

    @Autowired
    private NotificationBookmarkFixture notificationBookmarkFixture;

    @Autowired
    private NotificationFixture notificationFixture;

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("하나의 공지글을 즐겨찾기하고 즐겨찾기한 공지글을 삭제한다.")
    @Test
    void given_1_favorite_notification_when_delete_then_return_ok() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);

        List<NotificationBookmark> notificationBookmarks =
                notificationBookmarkFixture
                        .saveNotificationBookmarkByNotifications(notificationFixture.save15RandomNotification());

        Long deleteId = notificationBookmarks.get(0).getId();

        ExtractableResponse<Response> extract = given(documentationSpec)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "delete-favorite-notification",
                        getResponseFieldsSnippet()
                ))
                .when()
                .delete("api/notification-bookmark/" + deleteId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(extract.jsonPath().getString("data")).isEqualTo("id: " + deleteId + "가 잘 삭제가 되었습니다.");
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("잘 삭제됬다는 메시지를 드립니다.")
        );
    }
}
