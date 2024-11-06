package Team4SecurityApp.security.ServiceImpl;

import Team4SecurityApp.security.Domain.FingerDomain;
import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Dto.FaceDto;
import Team4SecurityApp.security.Dto.FingerDto;
import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Mapper.FingerMapper;
import Team4SecurityApp.security.Mapper.UserMapper;
import Team4SecurityApp.security.Repository.FingerRepository;
import Team4SecurityApp.security.Repository.UserRepository;
import Team4SecurityApp.security.Service.FingerService;
import Team4SecurityApp.security.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class FingerServiceImpl implements FingerService {
    @Autowired
    private final FingerRepository fingerRepository;
    @Autowired
    private final FingerMapper fingerMapper;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final MailService mailService;


    @Autowired
    public FingerServiceImpl(FingerRepository fingerRepository, FingerMapper fingerMapper, UserRepository userRepository, UserMapper userMapper, MailService mailService) {
        this.fingerRepository = fingerRepository;
        this.fingerMapper = fingerMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @Override
    public FingerDto saveFingerprint(FingerDto fingerDto) {
        FingerDomain fingerDomain = fingerMapper.fingerDtoToFingerDomain(fingerDto);

        if (fingerDomain == null) {
            throw new RuntimeException("Failed to map FingerDto to FingerDomain");
        }

        FingerDomain savedFingerDomain = fingerRepository.save(fingerDomain);

        if (savedFingerDomain == null) {
            throw new RuntimeException("Failed to save FingerDomain");
        }

        return fingerMapper.fingerDomainToFingerDto(savedFingerDomain);
    }

    @Override
    public FingerDto runPythonScript() {
        FingerDto fingerDto = new FingerDto();
        try {
            String cmd = "python C:\\Users\\CP\\Desktop\\tlg.py";
            Process process = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                output.append(s);
            }

            StringBuilder error = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                error.append(s);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                if (output.length() > 0) {
                    Long fingerId = Long.valueOf(output.toString().trim());
                    fingerDto.setFinger_id(fingerId);
                }
                return fingerDto;
            } else {
                throw new RuntimeException("Error: " + error.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage(), e);
        }
    }


    @Override
    public FingerDto scanAndCompare(Long user_id) {
        Optional<UserDomain> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()) {
            UserDomain user = userOptional.get();
            FingerDto scannedFingerDto = runPythonScript();
            if (scannedFingerDto.getFinger_id() == null) {
                return null;
            }
            Optional<FingerDomain> existingFingerOptional = Optional.ofNullable(fingerRepository.findByUser(user));
            if (existingFingerOptional.isPresent()) {
                FingerDomain existingFinger = existingFingerOptional.get();
                Long fingerId = existingFinger.getFinger_id();
                // Diğer gerekli işlemleri yapabilirsiniz (örneğin, user_id almak için existingFinger.getUser().getId() gibi)

                // FingerDto nesnesini oluşturup döndürebilirsiniz
                FingerDto fingerDto = new FingerDto();
                fingerDto.setFinger_id(fingerId);
                fingerDto.setUser_id(user_id);
                // Diğer gerekli alanları set edebilirsiniz
                System.out.println("çalıştı" + fingerDto);
                return fingerDto;
            } else {
                // Kullanıcı için parmak izi bulunamadığında uygun bir işleme stratejisi belirleyin
                return null;
            }
        } else {
            // Kullanıcı bulunamadığında uygun bir işleme stratejisi belirleyin
            return null;
        }
    }
}
