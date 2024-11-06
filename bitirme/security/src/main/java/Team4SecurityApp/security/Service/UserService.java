package Team4SecurityApp.security.Service;

import Team4SecurityApp.security.Dto.ApiResponse;
import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Login;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

        UserDto readByUsername(String username);
        ResponseEntity<ApiResponse<UserDto>> createUser(UserDto userDto);
        ResponseEntity<ApiResponse<UserDto>> login(Login login, UserDto foundUser, UserDto user);
        ResponseEntity<ApiResponse<UserDto>> deleteUser(Long user_id);
        ResponseEntity<ApiResponse<String>> changePassword(Long user_id, String newPassword);
        ResponseEntity<ApiResponse<List<UserDto>>> readAll();
        ResponseEntity<ApiResponse<UserDto>> updateUser(Long user_id, UserDto updatedUser);

}
