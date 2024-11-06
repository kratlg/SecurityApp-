package Team4SecurityApp.security.ServiceImpl;

import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Mapper.UserMapper;
import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Repository.MailRepository;
import Team4SecurityApp.security.PasswordUtil;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ForgotPasswordServiceImpl  {
    /*
    private static Map<String, String> verificationCodes = new HashMap<>();
    private final UserMapper userMapper;
    private final MailRepository mailRepository;
    private final MailServiceImpl mailService;



    public ForgotPasswordServiceImpl(UserMapper userMapper, MailRepository mailRepository, MailServiceImpl mailService) {
        this.userMapper = userMapper;
        this.mailRepository = mailRepository;
        this.mailService = mailService;
    }
// return html ile yapılıcak

    public void sendPasswordResetEmail(String mail) {
        UserDomain userDomain = mailRepository.findByMail(mail).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        UserDto user = userMapper.userDomainToUserDto(userDomain);
        mailService.sendMailToCurrentUser(user); // MailServiceImpl içindeki sendMailToCurrentUser metodunu kullanıyoruz
    }

    public void resetPassword(String mail, String userEnteredCode, String newPassword, String confirmPassword) {
        UserDomain userDomain = mailRepository.findByMail(mail).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        UserDto user = userMapper.userDomainToUserDto(userDomain);
        String verificationCodeFromEmail = verificationCodes.get(user.getMail());

        if (userEnteredCode.equals(verificationCodeFromEmail)) {
            if (newPassword.equals(confirmPassword)) {
                String hashedPassword = PasswordUtil.hashPassword(newPassword);
                userDomain.setPassword(hashedPassword);
                mailRepository.save(userDomain); // Veritabanında şifreyi güncelle
                verificationCodes.remove(user.getMail());
                System.out.println("Şifre başarıyla güncellendi.");
            } else {
                System.out.println("Girilen şifreler uyuşmuyor.");
            }
        } else {
            System.out.println("Doğrulama kodu yanlış.");
        }
    }
    */
}
