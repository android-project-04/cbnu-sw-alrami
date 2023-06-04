package cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.command;

import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.entity.CommunityBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityBookmarkJpaRepository extends JpaRepository<CommunityBookmark, Long> {
}
