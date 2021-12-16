package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<PersistentGenre, Long> {
}
