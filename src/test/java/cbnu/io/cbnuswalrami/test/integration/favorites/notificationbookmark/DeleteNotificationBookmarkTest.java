package cbnu.io.cbnuswalrami.test.integration.favorites.notificationbookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.NotificationBookmark;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.DeleteNotificationBookmarkService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.notification.NotificationFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.notificationbookmark.NotificationBookmarkFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DisplayName("공지사항 즐겨찾기 삭제 통합테스트")
class DeleteNotificationBookmarkTest extends DatabaseTestBase {

    @Autowired
    private MemberFindUtil memberFindUtil;

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private DeleteNotificationBookmarkService deleteNotificationBookmarkService;

    @Autowired
    private NotificationBookmarkFixture notificationBookmarkFixture;

    @Autowired
    private NotificationFixture notificationFixture;


    @DisplayName("본인이 저장하지 않은 글을 삭제하면 IllegalArgumentException을 던진다.")
    @Test
    void given_invalid_member_when_delete_then_return_illegalargument_exception() {
        // given
        Member member = signupFixture.signupMember(MemberFixture.createMember());
        List<NotificationBookmark> notificationBookmarks =
                notificationBookmarkFixture
                        .saveNotificationBookmarkByNotifications(notificationFixture.save15RandomNotification());

        Long deleteBookmarkId = notificationBookmarks.get(0).getId();

        // when
        // then
        Assertions.assertThatThrownBy(() -> deleteNotificationBookmarkService.deleteNotificationBookmark(member, RequestId.from(deleteBookmarkId)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인의 글이 아니거나 해당 id값의 북마크가 존재하지 않습니다.");
    }

    @DisplayName("본인이 저장하지 않은 글을 삭제하면 IllegalArgumentException을 던진다.")
    @Test
    void given_valid_member_when_delete_then_return_void() {
        // given
        Member member = memberFindUtil.findMemberByAuthentication();
        List<NotificationBookmark> notificationBookmarks =
                notificationBookmarkFixture
                        .saveNotificationBookmarkByNotifications(notificationFixture.save15RandomNotification());

        Long deleteBookmarkId = notificationBookmarks.get(0).getId();

        // when
        deleteNotificationBookmarkService.deleteNotificationBookmark(member, RequestId.from(deleteBookmarkId));
    }
}
