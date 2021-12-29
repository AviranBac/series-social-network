package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.SeriesStatus;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TvSeriesRepository extends JpaRepository<PersistentTvSeries, String>, JpaSpecificationExecutor<PersistentTvSeries> {

    @Query(value = "SELECT tv_series.* " +
                   "FROM (SELECT DISTINCT tv_seasons.series_id AS series_id" +
                   "      FROM tv_seasons, tv_episodes, watchlist_records " +
                   "      WHERE watchlist_records.user_name IN ?1 AND " +
                   "            watchlist_records.episode_id=tv_episodes.id AND " +
                   "            tv_episodes.season_id=tv_seasons.id) watchlist_series, tv_series " +
                   "WHERE tv_series.id = watchlist_series.series_id",
           nativeQuery = true)
    List<PersistentTvSeries> getWatchlistTvSeries(Set<String> usernames);

    @Query(value = "SELECT tv_series.* " +
                   "FROM (SELECT watchlist_series.id AS id, COUNT(*) AS count " +
                   "      FROM (SELECT DISTINCT watchlist_records.user_name, tv_seasons.series_id AS id " +
                   "            FROM tv_seasons, tv_episodes, watchlist_records " +
                   "            WHERE watchlist_records.user_name IN ?1 AND " +
                   "                  watchlist_records.episode_id=tv_episodes.id AND " +
                   "                  tv_episodes.season_id=tv_seasons.id) watchlist_series " +
                   "      GROUP BY watchlist_series.id) common_series, tv_series " +
                   "WHERE common_series.id=tv_series.id",
           nativeQuery = true)
    Page<PersistentTvSeries> getCommonSeriesAmongUsers(Set<String> usernames, Pageable pageable);

    @Query(value = "SELECT tv_series.* " +
                   "FROM (SELECT watchlist_series.id AS id, COUNT(*) AS count " +
                   "      FROM (SELECT DISTINCT watchlist_records.user_name, tv_seasons.series_id AS id " +
                   "            FROM tv_seasons, tv_episodes, watchlist_records " +
                   "            WHERE watchlist_records.episode_id=tv_episodes.id AND " +
                   "                  tv_episodes.season_id=tv_seasons.id) watchlist_series " +
                   "      GROUP BY watchlist_series.id) common_series, tv_series " +
                   "WHERE common_series.id=tv_series.id",
            nativeQuery = true)
    Page<PersistentTvSeries> getWatchedSeries(Pageable pageable);

    Page<PersistentTvSeries> getAllByOrderByVoteAverageDesc(Pageable pageable);

    Page<PersistentTvSeries> getAllByOrderByPopularityDesc(Pageable pageable);

    static Specification<PersistentTvSeries> nameStartsWith(String name) {
        return (tvSeries, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(tvSeries.get("name")), name.toLowerCase() + "%");
    }

    static Specification<PersistentTvSeries> seriesStatusIn(Collection<SeriesStatus> seriesStatusCriteria) {
        return (tvSeries, criteriaQuery, criteriaBuilder) -> {
            CriteriaBuilder.In<SeriesStatus> statusCriteriaBuilder = criteriaBuilder.in(tvSeries.get("status"));
            seriesStatusCriteria.forEach(statusCriteriaBuilder::value);
            return statusCriteriaBuilder;
        };
    }

    static Specification<PersistentTvSeries> genreIdIn(Collection<Integer> genreIdCriteria) {
        return (tvSeries, criteriaQuery, criteriaBuilder) ->  tvSeries.join("genres").in(genreIdCriteria);
    }
}
