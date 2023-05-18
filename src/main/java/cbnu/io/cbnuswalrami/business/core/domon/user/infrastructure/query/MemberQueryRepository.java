package cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.query;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Approval;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.web.user.presentation.response.MemberDto;
import cbnu.io.cbnuswalrami.business.web.user.presentation.response.QMemberDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted.*;
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
                                .and(member.isDeleted.eq(FALSE))))
                .fetchOne();
        return Optional.ofNullable(user);
    }

    public List<MemberDto> findMemberDtosByCursor(Long size) {
        return queryFactory.select(new QMemberDto(member.id, member.nickname.nickname, member.studentNumber.studentNumber))
                .from(member)
                .where(member.approval.eq(Approval.NO).and(member.isDeleted.eq(FALSE)))
                .limit(size)
                .orderBy(member.id.desc())
                .fetch();
    }

    public List<MemberDto> findMemberDtosByCursor(Long size, Long next) {
        return queryFactory.select(new QMemberDto(member.id, member.nickname.nickname, member.studentNumber.studentNumber))
                .from(member)
                .where(member.approval.eq(Approval.NO).and(member.id.lt(next)).and(member.isDeleted.eq(FALSE)))
                .limit(size)
                .orderBy(member.id.desc())
                .fetch();
    }

    public Boolean existMemberById(Long id) {
        List<Member> fetch = queryFactory.select(member)
                .from(member)
                .where(member.id.lt(id).and(member.isDeleted.eq(FALSE)))
                .limit(1L)
                .fetch();
        return fetch.size() > 0;
    }
}
