package cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.command;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Nickname;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.StudentNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(LoginId loginId);

    Optional<Member> findByStudentNumber(StudentNumber studentNumber);

    Optional<Member> findByNickname(Nickname nickname);
}
