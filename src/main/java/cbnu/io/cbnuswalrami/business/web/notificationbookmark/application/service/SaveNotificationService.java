package cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.business.core.domon.notification.infrastructure.command.NotificationJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.NotificationBookmark;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.infrastructure.command.NotificationBookmarkJpaRepository;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveNotificationService {

    private final NotificationBookmarkJpaRepository notificationBookmarkJpaRepository;

    private final NotificationJpaRepository notificationJpaRepository;

    @Transactional
    public NotificationDto saveNotificationBookmark(RequestId notificationId, Member member) {

        Notification notification = notificationJpaRepository.findById(notificationId.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 공지가 존재하지 않습니다."));

        NotificationBookmark notificationBookmark = new NotificationBookmark(member, notification);

        NotificationBookmark savedNotificationBookmark = notificationBookmarkJpaRepository.save(notificationBookmark);

        NotificationDto notificationDto = new NotificationDto(
                savedNotificationBookmark.getId(),
                notification.getTitle(),
                notification.getUrl()
        );

        return notificationDto;
    }
}
