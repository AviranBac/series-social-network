package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentFollow;
import mahat.aviran.common.entities.persistence.PersistentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<PersistentFollow, Long> {

    @Query(value = "SELECT id, username_from, username_to FROM follows where username_from=?1 AND username_to=?2", nativeQuery = true)
    PersistentFollow findFollow(String from, String to);

    List<Following> findAllByUsernameFrom(PersistentUser from);
    List<Follower> findAllByUsernameTo(PersistentUser to);

    interface Follower {
        PersistentUser getUsernameFrom();
    }

    interface Following {
        PersistentUser getUsernameTo();
    }
}