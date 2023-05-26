package cbnu.io.cbnuswalrami.business.core.domon.stopmember.entity;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Entity(name = "stop_member")
@NoArgsConstructor
public class StopMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stop_member_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;


    @Column(name = "created_at")
    ZonedDateTime createdAt;

    @Column(name = "last_stop_day")
    private ZonedDateTime lastStopDay;

    public StopMember(Member member, ZonedDateTime lastStopDay) {
        this.member = member;
        this.lastStopDay = lastStopDay;
    }

    public StopMember(Member member) {
        this.member = member;
        this.lastStopDay = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(30);
    }

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
