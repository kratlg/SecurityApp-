package Team4SecurityApp.security.ServiceImpl;

import Team4SecurityApp.security.Domain.FaceDomain;
import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Dto.FaceDto;
import Team4SecurityApp.security.Mapper.FaceMapper;
import Team4SecurityApp.security.Repository.FaceRepository;
import Team4SecurityApp.security.Repository.UserRepository;
import Team4SecurityApp.security.Service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class FaceServiceImpl implements FaceService {

    private final FaceRepository faceRepository;
    private final FaceMapper faceMapper;
    private final UserRepository userRepository;

    @Autowired
    public FaceServiceImpl(FaceRepository faceRepository, FaceMapper faceMapper, UserRepository userRepository) {
        this.faceRepository = faceRepository;
        this.faceMapper = faceMapper;
        this.userRepository = userRepository;
    }

    @Override
    public FaceDto runPythonScript() {
        // Implement logic to run the Python script and get face data
        FaceDto faceDto = new FaceDto();
        try {
            String cmd = "python C:\\Users\\CP\\Desktop\\tlg.py";
            Process process = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            String f;
            while ((f = stdInput.readLine()) != null) {
                output.append(f);
            }

            StringBuilder error = new StringBuilder();
            while ((f = stdError.readLine()) != null) {
                error.append(f);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                if (output.length() > 0) {
                    Long faceId = Long.valueOf(output.toString().trim());
                    faceDto.setFace_id(faceId);
                }
                return faceDto;
            } else {
                throw new RuntimeException("Error: " + error.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage(), e);
        }
    }

    @Override
    public FaceDto saveFace(FaceDto faceDto) {
        FaceDomain faceDomain = faceMapper.faceDtoToFaceDomain(faceDto);

        if (faceDomain == null) {
            throw new RuntimeException("Failed to map FingerDto to FingerDomain");
        }

        FaceDomain savedFaceDomain = faceRepository.save(faceDomain);

        if (savedFaceDomain == null) {
            throw new RuntimeException("Failed to save FingerDomain");
        }
        return faceMapper.faceDomainToFaceDto(savedFaceDomain);
    }


    @Override
    public FaceDto scanAndCompare(Long user_id) {
        Optional<UserDomain> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()) {
            UserDomain user = userOptional.get();
            FaceDto scannedFaceDto = runPythonScript(); // Yüz tarama işlemi gerçekleştirilir
            if (scannedFaceDto.getFace_id() == null) {
                return null;
            }

            Optional<FaceDomain> existingFaceOptional = Optional.ofNullable(faceRepository.findByUser(user));
            if (existingFaceOptional.isPresent()) {
                FaceDomain existingFace = existingFaceOptional.get();
                Long faceId = existingFace.getFace_id();
                // FaceDto nesnesini oluşturup döndürebilirsiniz
                FaceDto faceDto = new FaceDto();
                faceDto.setFace_id(faceId);
                faceDto.setUser_id(user_id);
                // Diğer gerekli alanları set edebilirsiniz
                System.out.println("çalıştı" + faceDto);
                return faceDto;
            } else {
                // Kullanıcı için yüz verisi bulunamadığında uygun bir işleme stratejisi belirleyin
                return null;
            }
        } else {
            // Kullanıcı bulunamadığında uygun bir işleme stratejisi belirleyin
            return null;
        }
    }
}
