package cbnu.io.cbnuswalrami.business.core.domon.stopmember.infrastructure.command;

import cbnu.io.cbnuswalrami.business.core.domon.stopmember.entity.StopMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopMemberJpaRepository extends JpaRepository<StopMember, Long> {
}
