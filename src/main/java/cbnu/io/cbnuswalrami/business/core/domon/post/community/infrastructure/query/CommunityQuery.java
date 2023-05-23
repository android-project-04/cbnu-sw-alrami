package cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.query;

import cbnu.io.cbnuswalrami.business.web.community.presentation.response.QResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted.*;
import static cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.QCommunity.community;
import static cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity.QCommunityCount.*;

@Repository
public class CommunityQuery {

    private final JPAQueryFactory queryFactory;

    public CommunityQuery(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<ResponseCommunity> findResponseCommunities(Long size) {
        return queryFactory.select(new QResponseCommunity(
                        community.id,
                        community.title.title,
                        community.description.description,
                        community.url,
                        community.createdAt,
                        communityCount.count
                ))
                .from(community, communityCount)
                .where(community.isDeleted.eq(FALSE).and(communityCount.community.id.eq(community.id)))
                .orderBy(community.id.desc())
                .limit(size)
                .fetch();
    }

    public List<ResponseCommunity> findResponseCommunities(Long next, Long size) {
        return queryFactory.select(new QResponseCommunity(
                        community.id,
                        community.title.title,
                        community.description.description,
                        community.url,
                        community.createdAt,
                        communityCount.count
                ))
                .from(community, communityCount)
                .where(community.id.lt(next).and(community.isDeleted.eq(FALSE)).and(communityCount.community.id.eq(community.id)))
                .orderBy(community.id.desc())
                .limit(size)
                .fetch();
    }

    public Boolean existByCommunityId(Long id) {
        List<Long> fetch = queryFactory.select(community.id)
                .from(community)
                .where(community.isDeleted.eq(FALSE).and(community.id.lt(id)))
                .limit(1)
                .fetch();
        return fetch.size() > 0L;
    }

    public Boolean existOldCommunityId(Long id) {
        List<Long> fetch = queryFactory.select(community.id)
                .from(community)
                .where(community.isDeleted.eq(FALSE).and(community.id.gt(id)))
                .limit(1)
                .fetch();
        return fetch.size() > 0L;
    }

    public List<ResponseCommunity> findResponseOldCommunities(Long next, Long size) {
        return queryFactory.select(new QResponseCommunity(
                        community.id,
                        community.title.title,
                        community.description.description,
                        community.url,
                        community.createdAt,
                        communityCount.count
                ))
                .from(community, communityCount)
                .where(community.id.gt(next).and(community.isDeleted.eq(FALSE)).and(communityCount.community.id.eq(community.id)))
                .orderBy(community.id.asc())
                .limit(size)
                .fetch();
    }

    public List<ResponseCommunity> findResponseOldCommunities(Long size) {
        return queryFactory.select(new QResponseCommunity(
                        community.id,
                        community.title.title,
                        community.description.description,
                        community.url,
                        community.createdAt,
                        communityCount.count
                ))
                .from(community, communityCount)
                .where(community.isDeleted.eq(FALSE).and(communityCount.community.id.eq(community.id)))
                .orderBy(community.id.asc())
                .limit(size)
                .fetch();
    }
}
