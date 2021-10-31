package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvSeriesRepository extends JpaRepository<PersistentTvSeries, String> {
}
