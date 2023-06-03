package cbnu.io.cbnuswalrami.business.web.notification.application.service.paging;

import cbnu.io.cbnuswalrami.business.core.domon.notification.infrastructure.query.NotificationQuery;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OldNotificationCursorPagingService {

    private final NotificationQuery notificationQuery;

    @Transactional(readOnly = true)
    public CursorResult findNotificationDtoByCursor(Cursor cursor) {
        List<NotificationDto> notificationDtos = getNotifications(cursor);
        Long lastIndex = notificationDtos.isEmpty() ? 0 : notificationDtos.get(notificationDtos.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex);
        return CursorResult.from(notificationDtos, hasNext, lastIndex);
    }

    public List<NotificationDto> getNotifications(Cursor cursor) {
        if (cursor.getNext() == null) {
            return notificationQuery.findOldNotificationDtoByCursor(cursor.getSize());
        }
        return notificationQuery.findOldNotificationDtoByCursor(cursor.getNext(), cursor.getSize());
    }

    public Boolean hasNext(Long id) {
        if (id <= 1 || id == null) return false;

        return notificationQuery.existOldNotificationById(id);
    }
}
