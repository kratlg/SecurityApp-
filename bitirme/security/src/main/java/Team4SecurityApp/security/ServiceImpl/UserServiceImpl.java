package Team4SecurityApp.security.ServiceImpl;

import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Dto.ApiResponse;
import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Login;
import Team4SecurityApp.security.Mapper.UserMapper;
import Team4SecurityApp.security.Repository.UserRepository;
import Team4SecurityApp.security.Service.MailService;
import Team4SecurityApp.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static Team4SecurityApp.security.PasswordUtil.hashPassword;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MailService mailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    public ResponseEntity<ApiResponse<UserDto>> createUser(UserDto userDto) {

        UserDomain user = userMapper.userDtoToUserDomain(userDto);

        // Rastgele bir Long sayı oluşturmak için bir Random nesnesi oluşturun
        Random random = new Random();
        long randomLong = random.nextLong();
        user.setUser_id(randomLong);

        try {
            userRepository.save(user);
            ApiResponse<UserDto> successResponse = new ApiResponse<>(userDto, HttpStatus.OK, "Kullanıcı oluşturuldu.");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<UserDto> errorResponse = new ApiResponse<>(null, HttpStatus.NOT_ACCEPTABLE, "Kullanıcı oluşturulurken hata oluştu.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<UserDto>> login(Login login, UserDto foundUser, UserDto user) {
        if (foundUser != null && hashPassword(login.getPassword()).equals(foundUser.getPassword())) {
            // Kullanıcı bulundu ve şifre doğru ise giriş başarılı
            ApiResponse<UserDto> successResponse = new ApiResponse<>(foundUser, HttpStatus.OK, "Kullanıcı giriş yaptı.");
            mailService.sendMailToCurrentUser(foundUser);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else {
            // Kullanıcı bulunamadı veya şifre yanlış ise giriş başarısız
            ApiResponse<UserDto> errorResponse = new ApiResponse<>(null, HttpStatus.UNAUTHORIZED, "Kullanıcı adı veya şifre yanlış.");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<UserDto>> updateUser(Long user_id, UserDto updatedUser) {
        // Kullanıcıyı kullanıcı adına göre veritabanında bulur.
        Optional<UserDomain> existingUserOptional = userRepository.findById(user_id);

        // Kullanıcı bulunamadıysa hata döner
        if (!existingUserOptional.isPresent()) {
            ApiResponse<UserDto> errorResponse = new ApiResponse<>(null, HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        UserDomain existingUser = existingUserOptional.get();
        // Kullanıcı bilgilerini günceller
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setName(updatedUser.getName());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setTel_no(updatedUser.getTel_no());
        existingUser.setMail(updatedUser.getMail());

        try {
            userRepository.save(existingUser);
            ApiResponse<UserDto> successResponse = new ApiResponse<>(updatedUser, HttpStatus.OK, "Kullanıcı başarılı bir şekilde güncellendi");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<UserDto> errorResponse = new ApiResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Kullanıcı bilgileri güncellenirken hata oluştu");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //buraya BAKARLAR
    @Override
    public ResponseEntity<ApiResponse<UserDto>> deleteUser(Long user_id) {
        try {
            userRepository.deleteById(user_id);
            ApiResponse<UserDto> successResponse = new ApiResponse<>(null,HttpStatus.OK, "Kullanıcı başarıyla silindi.");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<UserDto> errorResponse = new ApiResponse<>(null,HttpStatus.INTERNAL_SERVER_ERROR, "Kullanıcı silinirken bir hata oluştu.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDto readByUsername(String username) {
        // Kullanıcı adına göre kullanıcıyı veritabanından bul ve döndür
        UserDomain foundUser = userRepository.findByUsername(username);
        if (foundUser != null) {
            return userMapper.userDomainToUserDto(foundUser);
        } else {
            return null;
        }
    }

    // TODO: 24.04.2024 şifre sha256 ile update edilmiyor bakılmalı 
    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(Long user_id, String newPassword) {
        try {
            Optional<UserDomain> userOptional = (userRepository.findById(user_id));
            if (userOptional.isPresent()) {
                UserDomain user = userOptional.get();
                // Yeni şifre SHA-256 algoritmasıyla şifrelenir
                String hashedPassword = hashPassword(newPassword);
                user.setPassword(hashedPassword);
                // Kullanıcı güncellenir
                userRepository.save(user);
                return ResponseEntity.ok().body(new ApiResponse<>(hashedPassword, HttpStatus.OK, "Şifre başarılı bir şekilde değiştirildi."));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Hata durumunda yapılacak işlemler
            ApiResponse<String> errorResponse = new ApiResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Şifre değiştirilirken hata oluştu.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserDto>>> readAll() {
        List<UserDto> users = userRepository.findAll().stream()
                .map(userMapper::userDomainToUserDto) // userDomainToUserDto metodunu kullanıyoruz
                .collect(Collectors.toList());
        ApiResponse<List<UserDto>> response = new ApiResponse<>(users, HttpStatus.OK, "All users retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
