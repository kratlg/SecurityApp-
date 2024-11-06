package Team4SecurityApp.security.Controller;

import Team4SecurityApp.security.Dto.ApiResponse;
import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Login;
import Team4SecurityApp.security.Mapper.UserMapper;
import Team4SecurityApp.security.Repository.UserRepository;
import Team4SecurityApp.security.Service.MailService;
import Team4SecurityApp.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody Login login) {
        UserDto foundUserDto = userService.readByUsername(login.getUsername());
        UserDto foundUser = mailService.readByUsername(login.getUsername());
        return userService.login(login, foundUserDto, foundUser);
    }
    /**

     Yeni bir kullanıcı oluşturmak için bu metodu kullanır.*
     @param userDto Oluşturulacak kullanıcının bilgilerini içeren UserDto.
     @return ResponseEntity içinde ApiResponse ile oluşturulan kullanıcının durumunu içeren cevap.*/

    @PostMapping(value = "/createUser")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping(value = "/updateUser/{user_id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long user_id, @RequestBody UserDto updatedUser) {
        return userService.updateUser(user_id, updatedUser);
    }

    @DeleteMapping(value = "/deleteUser/{user_id}")
    public ResponseEntity<ApiResponse<UserDto>> deleteUser(@PathVariable Long user_id) {
        return userService.deleteUser(user_id);
    }
    @PutMapping(value = "/changePassword/{user_id}")
    public ResponseEntity<ApiResponse<String>> changePassword(@PathVariable Long user_id, @RequestBody String newPassword) {
        return userService.changePassword(user_id, newPassword);
    }
    @GetMapping(value = "/readAll")
    public ResponseEntity<ApiResponse<List<UserDto>>> readAll() {
        return userService.readAll();
    }

}
