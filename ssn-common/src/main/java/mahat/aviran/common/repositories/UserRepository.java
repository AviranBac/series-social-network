package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<PersistentUser, String> {

    @Query(value = "SELECT episode_id FROM watchlist_records where user_name=?1", nativeQuery = true)
    Set<String> getWatchlistEpisodeIds(String username);
}
