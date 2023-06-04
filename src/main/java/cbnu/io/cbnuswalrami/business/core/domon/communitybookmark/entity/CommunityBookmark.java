package cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.entity;

import cbnu.io.cbnuswalrami.business.core.domon.common.date.DateTime;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "community_bookmark")
@NoArgsConstructor
public class CommunityBookmark extends DateTime {
    @Id
    @Column(name = "community_bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;


    public CommunityBookmark(Member member, Community community) {
        this.member = member;
        this.community = community;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Community getCommunity() {
        return community;
    }
}
