package Team4SecurityApp.security.Service;

import Team4SecurityApp.security.Dto.FingerDto;
import Team4SecurityApp.security.Dto.UserDto;


public interface FingerService {
    FingerDto saveFingerprint(FingerDto fingerDto);
    FingerDto runPythonScript();
    FingerDto scanAndCompare(Long user_id);

}
