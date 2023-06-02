package cbnu.io.cbnuswalrami.test.integration.favorites.notificationbookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.NotificationDto;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.service.SaveNotificationService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("공지사항 즐겨찾기 저장 통합테스트")
class SaveNotificationBookmarkTest extends DatabaseTestBase {

    @Autowired
    private NotificationFixture notificationFixture;

    @Autowired
    private SaveNotificationService saveNotificationService;

    @Autowired
    private MemberFindUtil memberFindUtil;

    @DisplayName("공지글이 있을 때 즐겨찾기에 저장하면 저장한 공지글이 반환된다.")
    @Test
    void given_notification_when_save_to_favorite_then_return_saved_notification() {
        // given
        List<Notification> notifications = notificationFixture.save15RandomNotification();
        Long notificationId = notifications.get(1).getId();
        Member member = memberFindUtil.findMemberByAuthentication();

        // when
        NotificationDto notificationDto = saveNotificationService.saveNotificationBookmark(RequestId.from(notificationId), member);

        // then
        assertThat(notificationDto.getTitle()).isNotNull();
    }

    @DisplayName("index가 존재하지 않는 공지글을 즐겨찾기에 저장하려하면 IllegalArgumentException을 반환한다.")
    @Test
    void given_invalid_notification_id_when_save_to_favorite_then_return_illegalargument_exception() {
        // given
        List<Notification> notifications = notificationFixture.save15RandomNotification();
        Long notificationId = notifications.get(notifications.size() - 1).getId() + 10L;
        Member member = memberFindUtil.findMemberByAuthentication();

        assertThatThrownBy(() -> saveNotificationService.saveNotificationBookmark(RequestId.from(notificationId), member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id의 공지가 존재하지 않습니다.");
    }
}
