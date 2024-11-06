package Team4SecurityApp.security.ServiceImpl;

import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeValidator {
    @Autowired
    private final MailRepository mailRepository;

    public VerificationCodeValidator(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public void validateVerificationCode(UserDto user, String userEnteredCode) {
        // Kullanıcının e-posta adresini al
        String userEmail = user.getMail();

        // Kullanıcının e-posta adresine gönderilen doğrulama kodunu veritabanından al
        Optional<String> verificationCodeFromEmail = mailRepository.findVerificationCodeByMail(userEmail);

        // Eğer veritabanında bir doğrulama kodu bulunamadıysa
        if (verificationCodeFromEmail.isEmpty()) {
            System.out.println("Doğrulama kodu bulunamadı.");
            return;
        }

        // E-posta içeriğindeki doğrulama kodu
        String actualVerificationCode = verificationCodeFromEmail.get();

        // Kullanıcının girdiği kod ile e-posta içeriğindeki kodu karşılaştırma
        if (userEnteredCode.equals(actualVerificationCode)) {
            System.out.println("Doğrulama Başarılı, Kullanıcı Giriş Yapabilir.");
        } else {
            System.out.println("Doğrulama Başarısız, Lütfen Doğru Kodu Girin.");
        }
    }
}
