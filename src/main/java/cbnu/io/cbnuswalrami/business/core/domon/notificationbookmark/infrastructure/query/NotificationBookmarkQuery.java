package cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.infrastructure.query;

import cbnu.io.cbnuswalrami.business.core.domon.notification.QNotification;
import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.QNotificationBookmark;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.NotificationDto;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.QNotificationDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cbnu.io.cbnuswalrami.business.core.domon.notification.QNotification.*;
import static cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.QNotificationBookmark.*;

@Repository
@RequiredArgsConstructor
public class NotificationBookmarkQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<NotificationDto> findNotificationBookmarkByCursor(Long next, Long size) {

        return jpaQueryFactory.select(new QNotificationDto(
                        notification.id,
                        notification.title,
                        notification.url
                ))
                .from(notificationBookmark, notification)
                .where(notificationBookmark.id.lt(next).and(notificationBookmark.id.eq(notification.id)))
                .orderBy(notificationBookmark.id.desc())
                .limit(size)
                .fetch();
    }

    public List<NotificationDto> findNotificationBookmarkByCursor(Long size) {

        return jpaQueryFactory.select(new QNotificationDto(
                        notification.id,
                        notification.title,
                        notification.url
                ))
                .from(notificationBookmark, notification)
                .where(notificationBookmark.id.eq(notification.id))
                .orderBy(notificationBookmark.id.desc())
                .limit(size)
                .fetch();
    }

    public Boolean existNotificationBookmarkById(Long id) {
        List<Long> fetch = jpaQueryFactory.select(notificationBookmark.id)
                .from(notificationBookmark)
                .where(notificationBookmark.id.lt(id))
                .fetch();
        return fetch.size() > 0;
    }
}
