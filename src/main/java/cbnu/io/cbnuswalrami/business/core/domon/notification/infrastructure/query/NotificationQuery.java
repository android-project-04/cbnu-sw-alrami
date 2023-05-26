package cbnu.io.cbnuswalrami.business.core.domon.notification.infrastructure.query;

import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.NotificationDto;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.QNotificationDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cbnu.io.cbnuswalrami.business.core.domon.notification.QNotification.*;

@Repository
@RequiredArgsConstructor
public class NotificationQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<NotificationDto> findByNotificationDtoByCursor(Long next, Long size) {
        return jpaQueryFactory.select(new QNotificationDto(
                        notification.id,
                        notification.title,
                        notification.url
                ))
                .from(notification)
                .where(notification.id.lt(next))
                .orderBy(notification.id.desc())
                .limit(size)
                .fetch();
    }

    public List<NotificationDto> findByNotificationDtoByCursor(Long size) {
        return jpaQueryFactory.select(new QNotificationDto(
                        notification.id,
                        notification.title,
                        notification.url
                ))
                .from(notification)
                .orderBy(notification.id.desc())
                .limit(size)
                .fetch();
    }

    public Boolean existNotificationById(Long id) {
        List<Long> fetch = jpaQueryFactory.select(notification.id)
                .from(notification)
                .where(notification.id.lt(id))
                .limit(1L)
                .fetch();
        return fetch.size() > 0;
    }

    /**
     * 오래된 순으로 커서 페이징 조회
     */

    public List<NotificationDto> findOldNotificationDtoByCursor(Long next, Long size) {
        return jpaQueryFactory.select(new QNotificationDto(
                        notification.id,
                        notification.title,
                        notification.url
                ))
                .from(notification)
                .where(notification.id.gt(next))
                .orderBy(notification.id.asc())
                .limit(size)
                .fetch();
    }

    public List<NotificationDto> findOldNotificationDtoByCursor(Long size) {
        return jpaQueryFactory.select(new QNotificationDto(
                        notification.id,
                        notification.title,
                        notification.url
                ))
                .from(notification)
                .orderBy(notification.id.asc())
                .limit(size)
                .fetch();
    }

    public Boolean existOldNotificationById(Long id) {
        List<Long> fetch = jpaQueryFactory.select(notification.id)
                .from(notification)
                .where(notification.id.gt(id))
                .limit(1L)
                .fetch();
        return fetch.size() > 0;
    }
}
