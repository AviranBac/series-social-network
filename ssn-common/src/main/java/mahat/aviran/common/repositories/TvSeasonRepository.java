package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TvSeasonRepository extends JpaRepository<PersistentTvSeason, String> {

    @Query(value = "SELECT season_id FROM tv_seasons WHERE series_id=?1", nativeQuery = true)
    Set<String> getSeasonIdsBySeriesId(String seriesId);
}
