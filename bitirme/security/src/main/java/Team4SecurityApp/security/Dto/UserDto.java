package Team4SecurityApp.security.Dto;

import Team4SecurityApp.security.EnumRole.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private Long user_id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String tc_no;
    private String tel_no;
    private String mail;
    private Date birthday;
    private UserRole role;

}
