package Team4SecurityApp.security.Repository;

import Team4SecurityApp.security.Domain.FaceDomain;
import Team4SecurityApp.security.Domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceRepository extends JpaRepository<FaceDomain, Long> {
    FaceDomain findByUser(UserDomain user);
}


