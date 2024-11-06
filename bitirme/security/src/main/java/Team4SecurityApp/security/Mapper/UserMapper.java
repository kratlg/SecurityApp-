package Team4SecurityApp.security.Mapper;

import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.EnumRole.UserRole;
import Team4SecurityApp.security.PasswordUtil;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto userDomainToUserDto(UserDomain user) {
        UserDto userDto = new UserDto();
        userDto.setUser_id(user.getUser_id());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setTc_no(user.getTc_no());
        userDto.setTel_no(user.getTel_no());
        userDto.setMail(user.getMail());
        userDto.setBirthday(user.getBirthday());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public UserDomain userDtoToUserDomain(UserDto userDto) {
        UserDomain user = new UserDomain();
        user.setUser_id(userDto.getUser_id());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setUsername(userDto.getUsername());
        user.setPassword(PasswordUtil.hashPassword(userDto.getPassword())); // Şifreyi kriptolama
        user.setTc_no(userDto.getTc_no());
        user.setTel_no(userDto.getTel_no());
        user.setMail(userDto.getMail());
        user.setBirthday(userDto.getBirthday());
        user.setRole(UserRole.valueOf(String.valueOf(userDto.getRole()))); //rol bilgisi admin veya user
        return user;
    }

}

