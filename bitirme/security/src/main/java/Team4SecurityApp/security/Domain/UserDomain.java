package Team4SecurityApp.security.Domain;
import Team4SecurityApp.security.EnumRole.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "users")
public class UserDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long user_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "TC", nullable = false, unique = true)
    private String tc_no;

    @Column(name = "tel", nullable = false, unique = true)
    private String tel_no;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "birthDay", nullable = false)
    private Date birthday;

    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

}