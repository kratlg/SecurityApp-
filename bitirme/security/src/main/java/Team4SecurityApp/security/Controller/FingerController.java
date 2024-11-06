package Team4SecurityApp.security.Controller;

import Team4SecurityApp.security.Dto.FingerDto;
import Team4SecurityApp.security.Dto.UserDto;
import Team4SecurityApp.security.Repository.FingerRepository;
import Team4SecurityApp.security.Service.FingerService;
import Team4SecurityApp.security.Service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/finger")
public class FingerController {

    private final FingerService fingerService;
    private static final Logger logger = LoggerFactory.getLogger(FingerController.class);
    private final FingerRepository fingerRepository;
    private final MailService mailService;

    @Autowired
    public FingerController(FingerService fingerService, FingerRepository fingerRepository, MailService mailService) {
        this.fingerService = fingerService;
        this.fingerRepository = fingerRepository;
        this.mailService = mailService;
    }

    @PostMapping("/saveFingerprint")
    public ResponseEntity<String> saveFingerprint(@RequestBody Map<String, String> payload) {
        String userId = payload.get("user_id");

        // Run the Python script
        FingerDto fingerDto = fingerService.runPythonScript();
        if (fingerDto.getFinger_id() == null) {
            return ResponseEntity.status(500).body("Error running Python script");
        }

        // Save fingerprint data to the database
        fingerDto.setUser_id(Long.valueOf(userId));
        fingerService.saveFingerprint(fingerDto);

        return ResponseEntity.ok("Fingerprint saved successfully");
    }
    @GetMapping("/scanAndCompare/{user_id}")
    public ResponseEntity<FingerDto> scanAndCompare(@PathVariable Long user_id) {
        FingerDto result = fingerService.scanAndCompare(user_id);
        return ResponseEntity.ok(result);
    }

}

