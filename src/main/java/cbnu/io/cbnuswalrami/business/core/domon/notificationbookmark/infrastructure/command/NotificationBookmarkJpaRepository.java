package cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.infrastructure.command;

import cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity.NotificationBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationBookmarkJpaRepository extends JpaRepository<NotificationBookmark, Long> {
}
