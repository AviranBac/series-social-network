package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvEpisodeRepository extends JpaRepository<PersistentTvEpisode, String> {

    @Query(value = "SELECT tv_episodes.* FROM tv_episodes WHERE season_id=?1", nativeQuery = true)
    List<PersistentTvEpisode> getEpisodesBySeasonId(String seasonId);

    @Query(value = "SELECT tv_episodes.* " +
                   "FROM (SELECT id FROM tv_seasons WHERE series_id = ?1) as seasons, tv_episodes " +
                   "WHERE tv_episodes.season_id = seasons.id",
           nativeQuery = true)
    List<PersistentTvEpisode> getEpisodeIdsBySeriesId(String seriesId);

    @Query(value = "SELECT tv_episodes.* " +
                   "FROM tv_seasons, tv_episodes, watchlist_records " +
                   "WHERE watchlist_records.user_name=?1 AND " +
                   "      watchlist_records.episode_id=tv_episodes.id AND " +
                   "      tv_episodes.season_id=tv_seasons.id AND" +
                   "      tv_seasons.series_id=?2",
            nativeQuery = true)
    List<PersistentTvEpisode> getWatchlistEpisodesBySeries(String username, String seriesId);
}
