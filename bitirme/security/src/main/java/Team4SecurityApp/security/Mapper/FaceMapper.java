package Team4SecurityApp.security.Mapper;

import Team4SecurityApp.security.Domain.FaceDomain;
import Team4SecurityApp.security.Domain.UserDomain;
import Team4SecurityApp.security.Dto.FaceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FaceMapper {

    @Autowired
    private UserMapper userMapper;

    public FaceDto faceDomainToFaceDto(FaceDomain faceDomain) {
        FaceDto faceDto = new FaceDto();
        if (faceDomain.getUser() != null) {
            faceDto.setUser_id(faceDomain.getUser().getUser_id());
        }
        faceDto.setFace_id(faceDomain.getFace_id());
        return faceDto;
    }


    public FaceDomain faceDtoToFaceDomain(FaceDto faceDto) {
        FaceDomain faceDomain = new FaceDomain();
        UserDomain userDomain = new UserDomain();
        userDomain.setUser_id(faceDto.getUser_id());

        faceDomain.setUser(userDomain);
        faceDomain.setFace_id(faceDto.getFace_id());
        return faceDomain;
    }
}
