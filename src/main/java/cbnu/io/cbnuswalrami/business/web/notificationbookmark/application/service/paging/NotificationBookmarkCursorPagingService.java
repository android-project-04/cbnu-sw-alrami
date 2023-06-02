package cbnu.io.cbnuswalrami.business.web.notificationbookmark.application.service.paging;

import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.infrastructure.query.NotificationBookmarkQuery;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.notification.presentation.response.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationBookmarkCursorPagingService {
    
    private final NotificationBookmarkQuery notificationBookmarkQuery;

    @Transactional(readOnly = true)
    public CursorResult findNotificationBookmarksByCursor(Cursor cursor) {

        List<NotificationDto> notificationDtos = getNotificationDtos(cursor.getNext(), cursor.getSize());
        Long lastIndex = notificationDtos.isEmpty() ? 0 : notificationDtos.get(notificationDtos.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex);
        CursorResult cursorResult = CursorResult.from(notificationDtos, hasNext, lastIndex);

        return cursorResult;
    }

    private List<NotificationDto> getNotificationDtos(Long next, Long size) {
        if (next == null) {
            return notificationBookmarkQuery.findNotificationBookmarkByCursor(size);
        }
        return notificationBookmarkQuery.findNotificationBookmarkByCursor(next, size);
    }

    private Boolean hasNext(Long lastIndex) {
        if (lastIndex >= 1) {
            return false;
        }
        return notificationBookmarkQuery.existNotificationBookmarkById(lastIndex);
    }
}
