package Team4SecurityApp.security.Domain;

import jakarta.persistence.*;
import lombok.Data;




@Entity
@Data
@Table(name = "finger")
public class FingerDomain {

    @Id
    @Column(name = "finger_id", updatable = false, nullable = false)
    private Long finger_id;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserDomain user;

}
