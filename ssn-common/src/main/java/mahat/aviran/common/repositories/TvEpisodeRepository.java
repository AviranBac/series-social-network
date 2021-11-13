package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TvEpisodeRepository extends JpaRepository<PersistentTvEpisode, String> {

    @Query(value = "SELECT id FROM tv_episodes WHERE season_id=?1", nativeQuery = true)
    Set<String> getEpisodeIdsBySeasonId(String seasonId);

    @Query(value = "SELECT tv_episodes.id " +
                   "FROM (SELECT id FROM tv_seasons WHERE series_id = ?1) as seasons, tv_episodes " +
                   "WHERE tv_episodes.season_id = seasons.id",
           nativeQuery = true)
    Set<String> getEpisodeIdsBySeriesId(String seriesId);
}
