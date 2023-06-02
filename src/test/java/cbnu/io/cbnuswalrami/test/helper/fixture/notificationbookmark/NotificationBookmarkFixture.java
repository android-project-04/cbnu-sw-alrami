package cbnu.io.cbnuswalrami.test.helper.fixture.notificationbookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.NotificationBookmark;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.infrastructure.command.NotificationBookmarkJpaRepository;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationBookmarkFixture {

    @Autowired
    private NotificationBookmarkJpaRepository notificationBookmarkJpaRepository;

    @Autowired
    private MemberFindUtil memberFindUtil;

    public List<NotificationBookmark> saveNotificationBookmarkByNotifications(List<Notification> notifications) {
        Member member = memberFindUtil.findMemberByAuthentication();
        List<NotificationBookmark> notificationBookmarks = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationBookmark notificationBookmark = new NotificationBookmark(member, notification);
            notificationBookmarks.add(notificationBookmark);
        }

        return notificationBookmarkJpaRepository.saveAll(notificationBookmarks);
    }
}
