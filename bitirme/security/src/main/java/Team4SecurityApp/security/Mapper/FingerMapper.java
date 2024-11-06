package Team4SecurityApp.security.Mapper;

import Team4SecurityApp.security.Domain.FingerDomain;
import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Dto.FingerDto;
import org.springframework.stereotype.Component;

@Component
public class FingerMapper {

    public FingerDto fingerDomainToFingerDto(FingerDomain fingerDomain) {
        FingerDto fingerDto = new FingerDto();
        if (fingerDomain.getUser() != null) {
           fingerDto.setUser_id(fingerDomain.getUser().getUser_id());
       }
        fingerDto.setFinger_id(fingerDomain.getFinger_id());
        return fingerDto;
    }

    public FingerDomain fingerDtoToFingerDomain(FingerDto fingerDto) {
        FingerDomain fingerDomain = new FingerDomain();
        UserDomain userDomain = new UserDomain();
        userDomain.setUser_id(fingerDto.getUser_id());

        fingerDomain.setUser(userDomain);
        fingerDomain.setFinger_id(fingerDto.getFinger_id());
        return fingerDomain;
    }
}
