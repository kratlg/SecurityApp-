package Team4SecurityApp.security.Repository;

import Team4SecurityApp.security.Domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDomain, Long> {
    Optional<UserDomain> findById(Long user_id);
    UserDomain findByUsername(String name);


}
