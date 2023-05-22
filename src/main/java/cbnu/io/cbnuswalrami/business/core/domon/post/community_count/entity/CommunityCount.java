package cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity(name = "community_count")
public class CommunityCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_count_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "community_count")
    private Long count;

    public Long getId() {
        return id;
    }

    public Community getCommunity() {
        return community;
    }

    public Long getCount() {
        return count;
    }

    public CommunityCount(Community community) {
        this.community = community;
        this.count = 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommunityCount that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(community, that.community) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, community, count);
    }

    @Override
    public String toString() {
        return "CommunityCount{" +
                "id=" + id +
                '}';
    }
}
