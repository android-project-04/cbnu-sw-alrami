package cbnu.io.cbnuswalrami.business.core.domon.notification.infrastructure.command;

import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
}
