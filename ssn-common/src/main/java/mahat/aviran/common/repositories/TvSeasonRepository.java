package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvSeasonRepository extends JpaRepository<PersistentTvSeason, String> {

    @Query(value = "SELECT tv_seasons.* FROM tv_seasons WHERE series_id=?1", nativeQuery = true)
    List<PersistentTvSeason> getSeasonsBySeriesId(String seriesId);
}
