package Team4SecurityApp.security.Domain;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "face")
public class FaceDomain {

    @Id
    @Column(name = "face_id", updatable = false, nullable = false)
    private Long face_id;


    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserDomain user;

}
