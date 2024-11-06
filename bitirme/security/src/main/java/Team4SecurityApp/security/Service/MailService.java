package Team4SecurityApp.security.Service;

import Team4SecurityApp.security.Dto.UserDto;

public interface MailService {
    UserDto sendMailToCurrentUser(UserDto foundUser);

    UserDto readByUsername(String username);
}
