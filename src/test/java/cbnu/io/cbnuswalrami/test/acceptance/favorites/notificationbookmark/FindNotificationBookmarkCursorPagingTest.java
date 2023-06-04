package cbnu.io.cbnuswalrami.test.acceptance.favorites.notificationbookmark;

import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.notificationbookmark.NotificationBookmarkFixture;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("공지사항 즐겨찾기 커서 페이징 인수테스트")
class FindNotificationBookmarkCursorPagingTest extends AcceptanceTestBase {

    @Autowired
    private NotificationFixture notificationFixture;

    @Autowired
    private NotificationBookmarkFixture notificationBookmarkFixture;

    @Autowired
    private TokenProvider tokenProvider;


    @DisplayName("공지사항이 15개 있을 때 4개를 조회하면 4개의 공지사항 리스트를 반환한다.")
    @Test
    void given_15_notification_when_find_by_cursor_then_status_ok() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);
        List<Notification> notifications = notificationFixture.save15RandomNotification();
        notificationBookmarkFixture.saveNotificationBookmarkByNotifications(notifications);

        given(documentationSpec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .param("next", 6L)
                .param("size", 4L)
                .header("Authorization", "Bearer " + token)
                .filter(document(
                        "get-notification-favorite",
                        getRequestParametersSnippet(),
                        getResponseFieldsSnippet()
                ))
                .when()
                .get("/api/notification-bookmark")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    private static RequestParametersSnippet getRequestParametersSnippet() {
        return requestParameters(
                parameterWithName("next").description("해당 커뮤니티 게시물의 id값 부터 검색합니다, null값을 넣을시 최신 회원부터 검색").optional(),
                parameterWithName("size").description("null값을 넣을 시 기본 사이즈 10개입니다, 페이지에서 몇개를 검색할지 설정하는 값").optional()
        );
    }

    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 요청할 수 있는 멤버가 있는지"),
                fieldWithPath("data.lastIndex").type(JsonFieldType.NUMBER).description("요청한 멤버의 마지막 index번호(해당 번호를 next값으로 쓰면 된다)"),
                fieldWithPath("data.values[].id").type(JsonFieldType.NUMBER).description("공지사항 게시물 id"),
                fieldWithPath("data.values[].title").type(JsonFieldType.STRING).description("공지사항 게시물 제목"),
                fieldWithPath("data.values[].url").type(JsonFieldType.STRING).description("공지사항 게시물 url")
        );
    }
}
