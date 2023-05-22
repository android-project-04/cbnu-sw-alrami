package cbnu.io.cbnuswalrami.business.core.domon.post.community_count.infrastructure;

import cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity.CommunityCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityCountJpaRepository extends JpaRepository<CommunityCount, Long> {
}
