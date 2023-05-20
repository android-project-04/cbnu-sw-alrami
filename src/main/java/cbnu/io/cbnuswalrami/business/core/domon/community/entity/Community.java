package cbnu.io.cbnuswalrami.business.core.domon.community.entity;

import cbnu.io.cbnuswalrami.business.core.domon.common.date.DateTime;
import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Table(name = "community")
@NoArgsConstructor
public class Community extends DateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url", nullable = true)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_deleted")
    private Deleted isDeleted;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Community(String title, String description, String url, Member member) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.isDeleted = Deleted.FALSE;
        this.member = member;
    }


    public void changeToDeleteTrue() {
        this.isDeleted = Deleted.TRUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Community community)) return false;
        return Objects.equals(id, community.id) &&
                Objects.equals(title, community.title) &&
                Objects.equals(description, community.description) &&
                Objects.equals(url, community.url) &&
                isDeleted == community.isDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, url, isDeleted);
    }

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                '}';
    }
}
