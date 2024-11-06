package Team4SecurityApp.security.Service;

import Team4SecurityApp.security.Domain.FaceDomain;
import Team4SecurityApp.security.Dto.FaceDto;

public interface FaceService {
    FaceDto runPythonScript();
    FaceDto saveFace(FaceDto faceDto);
    FaceDto scanAndCompare(Long user_id);

}
