package cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.query;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Approval;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static cbnu.io.cbnuswalrami.business.core.domon.user.entity.QMember.*;


@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    public Optional<Member> findValidLoginUserByLoginId(LoginId loginId) {
        Member user = queryFactory.select(member)
                .from(member)
                .where(member.loginId.eq(loginId)
                        .and(member.approval.eq(Approval.OK)
                                .and(member.isDeleted.eq(Deleted.FALSE))))
                .fetchOne();
        return Optional.ofNullable(user);
    }
}
