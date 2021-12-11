package mahat.aviran.common.repositories;

import mahat.aviran.common.entities.persistence.PersistentUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<PersistentUser, String>, JpaSpecificationExecutor<PersistentUser> {

    static Specification<PersistentUser> userNameStartsWith(String userName) {
        return (user, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(user.get("userName")), userName.toLowerCase() + "%");
    }

    static Specification<PersistentUser> firstNameStartsWith(String firstName) {
        return (user, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(user.get("firstName")), firstName.toLowerCase() + "%");
    }

    static Specification<PersistentUser> lastNameStartsWith(String lastName) {
        return (user, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(user.get("lastName")), lastName.toLowerCase() + "%");
    }
}
