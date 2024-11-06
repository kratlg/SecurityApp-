package Team4SecurityApp.security.Config;

import Team4SecurityApp.security.Mapper.UserMapper;
import Team4SecurityApp.security.Repository.UserRepository;
import Team4SecurityApp.security.Service.MailService;
import Team4SecurityApp.security.Service.UserService;
import Team4SecurityApp.security.ServiceImpl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private MailService mailService;

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public UserService userService() {

        return new UserServiceImpl(userRepository, userMapper, mailService);
    }
}
