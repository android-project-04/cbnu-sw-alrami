package cbnu.io.cbnuswalrami.business.core.domon.notificationbookmark.entity;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.notification.Notification;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
public class NotificationBookmark {

    @Id
    @Column(name = "notification_bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Notification notification;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public NotificationBookmark(Member member, Notification notification) {
        this.member = member;
        this.notification = notification;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Notification getNotification() {
        return notification;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
