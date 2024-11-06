package Team4SecurityApp.security.Repository;

import Team4SecurityApp.security.Domain.FingerDomain;
import Team4SecurityApp.security.Domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FingerRepository extends JpaRepository<FingerDomain, Long> {
    FingerDomain findByUser(UserDomain user);
}
