package cbnu.io.cbnuswalrami.business.web.notificationbookmark.application;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.NotificationBookmark;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.infrastructure.command.NotificationBookmarkJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.infrastructure.query.NotificationBookmarkQuery;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteNotificationBookmarkService {

    private final NotificationBookmarkQuery notificationBookmarkQuery;
    private final NotificationBookmarkJpaRepository notificationBookmarkJpaRepository;

    @Transactional
    public void deleteNotificationBookmark(Member member, RequestId notificationBookmarkId) {
        NotificationBookmark notificationBookmark = notificationBookmarkJpaRepository.findById(notificationBookmarkId.getId())
                .filter(x -> x.getMember().getId().equals(member.getId()))
                .orElseThrow(() -> new IllegalArgumentException("본인의 글이 아니거나 해당 id값의 북마크가 존재하지 않습니다."));

        notificationBookmarkJpaRepository.delete(notificationBookmark);
    }
}
