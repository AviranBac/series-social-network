package mahat.aviran.tvseriesfetcher.repositories;

import mahat.aviran.tvseriesfetcher.entities.persistance.PersistentGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<PersistentGenre, Long> {
}
