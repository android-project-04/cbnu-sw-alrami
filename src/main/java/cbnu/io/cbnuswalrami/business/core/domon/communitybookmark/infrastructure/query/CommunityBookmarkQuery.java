package cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.query;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.QResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation.response.QResponseCommunityBookmark;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation.response.ResponseCommunityBookmark;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted.*;
import static cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.entity.QCommunityBookmark.*;
import static cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.QCommunity.*;
import static cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity.QCommunityCount.*;

@Repository
public class CommunityBookmarkQuery {

    private final JPAQueryFactory queryFactory;

    public CommunityBookmarkQuery(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<ResponseCommunityBookmark> findResponseCommunitiesByCursor(
            Long next,
            Long size,
            Member member
    ) {
        System.out.println("CommunityBookmarkQuery.findResponseCommunitiesByCursor");
        return queryFactory.select(new QResponseCommunityBookmark(
                        communityBookmark.id,
                        communityBookmark.community.title.title,
                        communityBookmark.community.description.description,
                        communityBookmark.community.url,
                        communityBookmark.community.createdAt,
                        communityCount.count
                )).from(communityBookmark, communityCount, community)
                .where(
                        communityBookmark.community.id.eq(community.id)
                                .and(community.isDeleted.eq(FALSE)
                                        .and(community.id.eq(communityCount.community.id))
                                        .and(communityBookmark.id.lt(next)
                                                .and(communityBookmark.member.eq(member))
                                        )))
                .limit(size)
                .orderBy(communityBookmark.id.desc())
                .fetch();

    }

    public List<ResponseCommunityBookmark> findResponseCommunitiesByCursor(
            Long size,
            Member member
    ) {
        return queryFactory.select(new QResponseCommunityBookmark(
                        communityBookmark.id,
                        communityBookmark.community.title.title,
                        communityBookmark.community.description.description,
                        communityBookmark.community.url,
                        communityBookmark.community.createdAt,
                        communityCount.count
                )).from(community, communityCount, communityBookmark)
                .where(
                        community.id.eq(communityBookmark.community.id)
                                .and(community.isDeleted.eq(FALSE)
                                        .and(community.id.eq(communityCount.community.id))
                                        .and(communityBookmark.member.eq(member))
                                ))
                .limit(size)
                .orderBy(communityBookmark.id.desc())
                .fetch();

    }

    public Boolean hasNext(Long id) {
        List<Long> fetch = queryFactory.select(communityBookmark.id)
                .from(community, communityBookmark)
                .where(
                        community.id.eq(communityBookmark.community.id)
                                .and(communityBookmark.id.lt(id)
                                        .and(community.isDeleted.eq(FALSE))
                                )).fetch();
        return fetch.size() > 0;
    }

    public Optional<ResponseCommunity> findResponseCommunityByBookmarkId(Long id) {
        ResponseCommunity responseCommunity = queryFactory.select(new QResponseCommunity(
                        communityBookmark.community.id,
                        communityBookmark.community.title.title,
                        communityBookmark.community.description.description,
                        communityBookmark.community.url,
                        communityBookmark.community.createdAt,
                        communityCount.count
                )).from(communityBookmark, community, communityCount)
                .where(
                        communityBookmark.id.eq(id)
                                .and(communityBookmark.community.id.eq(community.id))
                                .and(community.id.eq(communityCount.community.id))
                                .and(community.isDeleted.eq(FALSE))
                ).fetchOne();

        return Optional.ofNullable(responseCommunity);
    }
}
