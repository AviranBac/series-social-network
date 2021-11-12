package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<PersistentUser, String> {
}
