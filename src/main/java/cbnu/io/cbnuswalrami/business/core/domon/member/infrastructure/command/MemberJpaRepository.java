package cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Nickname;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.StudentNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(LoginId loginId);

    Optional<Member> findByStudentNumber(StudentNumber studentNumber);

    Optional<Member> findByNickname(Nickname nickname);
}
