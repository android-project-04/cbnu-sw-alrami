package cbnu.io.cbnuswalrami.test.integration.favorites.notificationbookmark;

import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.NotificationBookmark;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.service.paging.NotificationBookmarkCursorPagingService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.notificationbookmark.NotificationBookmarkFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("즐겨찾기 공지사항 커서 페이징 조회 통합테스트")
class NotificationBookmarkCursorPagingTest extends DatabaseTestBase {

    @Autowired
    private NotificationBookmarkFixture notificationBookmarkFixture;

    @Autowired
    private NotificationFixture notificationFixture;

    @Autowired
    private NotificationBookmarkCursorPagingService notificationBookmarkCursorPagingService;

    @DisplayName("15개의 즐겨찾기 공지사항이 있을 때 size를 4개로 했을 때 4개를 반환한다.")
    @Test
    void given_size_4_when_find_then_bookmark_notifications_4() {
        List<Notification> notifications = notificationFixture.save15RandomNotification();
        List<NotificationBookmark> notificationBookmarks = notificationBookmarkFixture.saveNotificationBookmarkByNotifications(notifications);


        CursorResult cursorResult = notificationBookmarkCursorPagingService.findNotificationBookmarksByCursor(Cursor.from(null, 4L));
        assertThat(cursorResult.getValues()).hasSize(4);
    }

    @DisplayName("즐겨찾기의 고지사항이 하나도 없으면 lastIndex값이 0이다.")
    @Test
    void given_empty_notification_bookmark_when_find_then_last_index_should_be_0() {
        CursorResult cursorResult = notificationBookmarkCursorPagingService.findNotificationBookmarksByCursor(Cursor.from(null, 4L));
        assertThat(cursorResult.getLastIndex()).isEqualTo(0L);
    }

    @DisplayName("즐겨찾기의 고지사항이 하나도 없으면 values가 빈 배열이다.")
    @Test
    void given_empty_notification_bookmark_when_find_then_values_is_empty_array() {
        CursorResult cursorResult = notificationBookmarkCursorPagingService.findNotificationBookmarksByCursor(Cursor.from(null, 4L));
        assertThat(cursorResult.getValues()).isEmpty();
    }
}
