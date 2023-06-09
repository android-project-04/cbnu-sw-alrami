package cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.query;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.QResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.QResponseMyPageCommunity;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.ResponseMyPageCommunity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted.*;
import static cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.QCommunity.community;
import static cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity.QCommunityCount.*;
import static cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType.*;

@Repository
public class CommunityQuery {

    private final JPAQueryFactory queryFactory;

    public CommunityQuery(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<ResponseCommunity> findResponseCommunities(Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.isDeleted.eq(FALSE)
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(COMMUNITY))
                )
                .orderBy(community.id.desc())
                .limit(size)
                .fetch();
    }

    public List<ResponseCommunity> findResponseCommunities(Long next, Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.id.lt(next)
                                .and(community.isDeleted.eq(FALSE))
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(COMMUNITY))
                )
                .orderBy(community.id.desc())
                .limit(size)
                .fetch();
    }

    public Boolean existByCommunityId(Long id, CommunityType communityType) {
        List<Long> fetch = queryFactory.select(community.id)
                .from(community)
                .where(
                        community.isDeleted.eq(FALSE)
                                .and(community.id.lt(id))
                                .and(community.communityType.eq(communityType))
                )
                .limit(1)
                .fetch();
        return fetch.size() > 0L;
    }

    public Boolean existOldCommunityId(Long id, CommunityType communityType) {
        List<Long> fetch = queryFactory.select(community.id)
                .from(community)
                .where(
                        community.isDeleted.eq(FALSE)
                                .and(community.id.gt(id))
                                .and(community.communityType.eq(communityType))
                )
                .limit(1)
                .fetch();
        return fetch.size() > 0L;
    }

    public List<ResponseCommunity> findResponseOldCommunities(Long next, Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.id.gt(next)
                                .and(community.isDeleted.eq(FALSE))
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(COMMUNITY))
                )
                .orderBy(community.id.asc())
                .limit(size)
                .fetch();
    }

    public List<ResponseCommunity> findResponseOldCommunities(Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.isDeleted.eq(FALSE)
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(COMMUNITY))
                )
                .orderBy(community.id.asc())
                .limit(size)
                .fetch();
    }

    public ResponseCommunity findCommunityById(Long id) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.id.eq(id)
                                .and(community.isDeleted.eq(FALSE)
                                        .and(communityCount.community.id.eq(community.id)))
                )
                .fetchOne();
    }


    /**
     * 취업 정보 커뮤니티 페이징 조회
     */

    public List<ResponseCommunity> findResponseEmploymentCommunities(Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.isDeleted.eq(FALSE)
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(EMPLOYMENT))
                )
                .orderBy(community.id.desc())
                .limit(size)
                .fetch();
    }

    private JPAQuery<ResponseCommunity> selectResponseCommunity() {
        return queryFactory
                .select(new QResponseCommunity(
                community.id,
                community.title.title,
                community.description.description,
                community.url,
                community.createdAt,
                communityCount.count
        ));
    }

    public List<ResponseCommunity> findResponseEmploymentCommunities(Long next, Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.id.lt(next)
                                .and(community.isDeleted.eq(FALSE))
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(EMPLOYMENT))
                )
                .orderBy(community.id.desc())
                .limit(size)
                .fetch();
    }

    /**
     * 취업정보 커뮤니티(오래된순) 커서 페이징 조회
     */
    public List<ResponseCommunity> findResponseOldEmploymentCommunities(Long next, Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.id.gt(next)
                                .and(community.isDeleted.eq(FALSE))
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(EMPLOYMENT))
                )
                .orderBy(community.id.asc())
                .limit(size)
                .fetch();
    }

    public List<ResponseCommunity> findResponseOldEmploymentCommunities(Long size) {
        return selectResponseCommunity()
                .from(community, communityCount)
                .where(
                        community.isDeleted.eq(FALSE)
                                .and(communityCount.community.id.eq(community.id))
                                .and(community.communityType.eq(EMPLOYMENT))
                )
                .orderBy(community.id.asc())
                .limit(size)
                .fetch();
    }

    public Boolean existOldEmploymentCommunityId(Long id) {
        List<Long> fetch = queryFactory.select(community.id)
                .from(community)
                .where(
                        community.isDeleted.eq(FALSE)
                                .and(community.id.gt(id))
                                .and(community.communityType.eq(EMPLOYMENT))
                )
                .limit(1)
                .fetch();
        return fetch.size() > 0L;
    }

    /**
     * Member의 MyPage에서 조회할 때 쿼리
     */
    public List<ResponseMyPageCommunity> findMyPageCommunityByMember(Member member) {
        return queryFactory.select(new QResponseMyPageCommunity(community.id, community.title.title))
                .from(community)
                .where(
                        community.member.eq(member)
                                .and(community.isDeleted.eq(FALSE))
                ).fetch();
    }
}
