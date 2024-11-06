package Team4SecurityApp.security.ServiceImpl;

import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Mapper.UserMapper;
import Team4SecurityApp.security.Repository.MailRepository;
import Team4SecurityApp.security.Repository.UserRepository;
import Team4SecurityApp.security.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private final MailRepository mailRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public MailServiceImpl(MailRepository mailRepository, UserMapper userMapper, UserRepository userRepository) {
        this.mailRepository = mailRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto sendMailToCurrentUser(UserDto foundUser) {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        final String username = "koraycetintr@gmail.com";
        final String password = "toeraldwduyxphqe";


        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);


            String currentUserEmail = mailRepository.findByMail(foundUser.getMail())
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"))
                    .getMail();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(currentUserEmail));
            message.setSubject("Doğrulama Kodu");

            String verificationCode = generateRandomCode();
            String mailContent = "Merhaba,\n\nDoğrulama kodunuz: " + verificationCode;

            message.setText(mailContent);
            Transport.send(message);
            System.out.println("Mail başarıyla gönderildi.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return foundUser;
    }

    private static String generateRandomCode() {
        Random random = ThreadLocalRandom.current();
        int min = 100000;
        int max = 999999;
        int randomCode = random.nextInt(max - min + 1) + min;
        return String.valueOf(randomCode);
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
}
