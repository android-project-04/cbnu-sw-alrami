package cbnu.io.cbnuswalrami.test.integration.notification;

import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notification.application.service.paging.NotificationCursorPagingService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("공지사항 리스트 커서 페이징 조회 통합 테스트")
public class NotificationFindListTest extends DatabaseTestBase {

    @Autowired
    private NotificationCursorPagingService notificationCursorPagingService;

    @Autowired
    private NotificationFixture notificationFixture;

    @DisplayName("15개의 공지가 있을 때 10개, 5개 씩 조회했을 때 총 합이 15개가 되야한다.")
    @Test
    public void given_15_notification_when_find_then_15_notification() {
        // given - cursor
        List<Notification> notifications = notificationFixture.save15RandomNotification();
        Cursor cursor1 = Cursor.from(null, null);

        // when - find
        CursorResult cursorResult = notificationCursorPagingService.findNotificationDtoByCursor(cursor1);
        Cursor cursor2 = Cursor.from(cursorResult.getLastIndex(), null);

        // then
        assertThat(cursorResult.getValues().size()).isEqualTo(10L);
        assertThat(notificationCursorPagingService
                .findNotificationDtoByCursor(cursor2)
                .getValues()
                .size()
        ).isEqualTo(5L);
    }


    @DisplayName("커서로 size값이 마이너스가 들어오면 10개를 반환한다.")
    @Test
    public void given_minus_size_when_find_then_10_size() {
        // given
        notificationFixture.save15RandomNotification();
        Long size = -1L;

        // when
        int actual = notificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(null, size)).getValues().size();

        // then
        assertThat(actual).isEqualTo(10L);
    }

    @DisplayName("next값 6에 size 5이면 hasNext가 false이다.")
    @Test
    public void given_next_5_size_5_when_find_then_hast_next_false() {
        // given
        Cursor cursor = Cursor.from(6L, 5L);
        notificationFixture.save15RandomNotification();

        // when
        Boolean actual = notificationCursorPagingService.findNotificationDtoByCursor(cursor).getHasNext();

        // then
        assertThat(actual).isEqualTo(false);

    }
}
