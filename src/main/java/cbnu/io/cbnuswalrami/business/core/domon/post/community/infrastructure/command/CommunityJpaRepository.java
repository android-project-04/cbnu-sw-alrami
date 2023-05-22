package cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.command;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityJpaRepository extends JpaRepository<Community, Long> {
}
