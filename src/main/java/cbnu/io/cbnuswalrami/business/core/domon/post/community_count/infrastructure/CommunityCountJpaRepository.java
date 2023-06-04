package cbnu.io.cbnuswalrami.business.core.domon.post.community_count.infrastructure;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity.CommunityCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityCountJpaRepository extends JpaRepository<CommunityCount, Long> {

    Optional<CommunityCount> findByCommunity(Community community);
}
