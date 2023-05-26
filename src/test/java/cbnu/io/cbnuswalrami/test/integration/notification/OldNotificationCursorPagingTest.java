package cbnu.io.cbnuswalrami.test.integration.notification;

import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notification.application.service.paging.OldNotificationCursorPagingService;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("공지사항 커서페이징 조회(오래된 순) 통합테스트")
public class OldNotificationCursorPagingTest extends DatabaseTestBase {

    @Autowired
    private OldNotificationCursorPagingService oldNotificationCursorPagingService;

    @Autowired
    private NotificationFixture notificationFixture;

    @DisplayName("15개의 데이터가 저장 되있을 떄 next=null size1을 가져오면  size는 1이다.")
    @ParameterizedTest
    @ValueSource(longs = 1)
    void given_data_15_when_size_1_then_size_1(Long size) {
        notificationFixture.save15RandomNotification();

        CursorResult cursorResult = oldNotificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(null, size));

        assertThat(cursorResult.getValues().size()).isEqualTo(1);
    }

    @DisplayName("데이터가 15개일 때 10개, 5개 씩 가져올 수 있다.")
    @Test
    void given_15_notification_when_find_15_then_size_15() {
        notificationFixture.save15RandomNotification();

        CursorResult cursorResult1 = oldNotificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(null, null));
        CursorResult cursorResult2 = oldNotificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(cursorResult1.getLastIndex(), null));

        assertThat(cursorResult1.getValues().size()).isEqualTo(10);
        assertThat(cursorResult2.getValues().size()).isEqualTo(5);
    }

    @DisplayName("데이터가 15개일 때 next값이 5이면 hasNext 값이 false이다.")
    @Test
    void given_15_notification_next_5_when_find_then_has_next_false() {
        notificationFixture.save15RandomNotification();

        CursorResult cursorResult = oldNotificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(5L, null));

        assertThat(cursorResult.getHasNext()).isEqualTo(false);
    }

    @DisplayName("데이터가 15개일 때 next값이 4이면 hasNext값이 true이다.")
    @Test
    void given_15_notification_next_4_when_find_then_has_next_true() {
        notificationFixture.save15RandomNotification();

        CursorResult cursorResult = oldNotificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(4L, null));

        assertThat(cursorResult.getHasNext()).isEqualTo(true);
    }

    @DisplayName("15개의 데이터에서 3개를 가져오면 lastIndex 값이 3이다.")
    @Test
    void given_15_notification_when_size_3_then_last_index_3() {
        notificationFixture.save15RandomNotification();

        CursorResult cursorResult = oldNotificationCursorPagingService.findNotificationDtoByCursor(Cursor.from(null, 3L));

        assertThat(cursorResult.getLastIndex()).isEqualTo(3L);
    }
}
