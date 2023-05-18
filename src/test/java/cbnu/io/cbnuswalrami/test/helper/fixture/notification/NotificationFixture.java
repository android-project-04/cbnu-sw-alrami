package cbnu.io.cbnuswalrami.test.helper.fixture.notification;

import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import cbnu.io.cbnuswalrami.business.core.domon.notification.infrastructure.command.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class NotificationFixture {

    private final NotificationJpaRepository notificationJpaRepository;

    public NotificationFixture(NotificationJpaRepository notificationJpaRepository) {
        this.notificationJpaRepository = notificationJpaRepository;
    }

    public List<Notification> save15RandomNotification() {
        List<Notification> notifications = new ArrayList<>();
        IntStream.rangeClosed(1, 15)
                .forEach(x -> notifications.add(
                        new Notification(
                                "abcd" + x,
                                "www.ac.kr" + x
                        )
                ));
        return notificationJpaRepository.saveAll(notifications);
    }

}
