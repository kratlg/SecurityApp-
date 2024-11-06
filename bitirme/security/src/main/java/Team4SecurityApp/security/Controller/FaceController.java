package Team4SecurityApp.security.Controller;

import Team4SecurityApp.security.Dto.FaceDto;
import Team4SecurityApp.security.Repository.FaceRepository;
import Team4SecurityApp.security.Repository.FingerRepository;
import Team4SecurityApp.security.Service.FaceService;
import Team4SecurityApp.security.Service.FingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/face")
public class FaceController {

    private final FaceService faceService;
    private final FingerService fingerService;
    private static final Logger logger = LoggerFactory.getLogger(FaceController.class);
    private final FaceRepository faceRepository;
    private final FingerRepository fingerRepository;

    @Autowired
    public FaceController(FaceService faceService, FingerService fingerService, FaceRepository faceRepository, FingerRepository fingerRepository) {
        this.faceService = faceService;
        this.fingerService = fingerService;
        this.faceRepository = faceRepository;
        this.fingerRepository = fingerRepository;
    }

    @PostMapping("/saveFace")
    public ResponseEntity<String> saveFace(@RequestBody Map<String, String> payload) {
        String userId = payload.get("user_id");

        // Run the Python script for face recognition
        FaceDto faceDto = faceService.runPythonScript();
        if (faceDto.getFace_id() == null) {
            return ResponseEntity.status(500).body("Error running Python script");
        }

        // Save face data to the database
        faceDto.setUser_id(Long.valueOf(userId));
        faceService.saveFace(faceDto);

        return ResponseEntity.ok("Face data saved successfully");
    }


    @GetMapping("/scanAndCompareFace/{user_id}")
    public ResponseEntity<FaceDto> scanAndCompareFace(@PathVariable Long user_id) {
        FaceDto result = faceService.scanAndCompare(user_id);
        return ResponseEntity.ok(result);
    }
}
