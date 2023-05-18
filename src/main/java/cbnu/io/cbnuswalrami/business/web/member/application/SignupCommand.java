package cbnu.io.cbnuswalrami.business.web.member.application;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.member.presentation.request.SignupRequest;
import org.springframework.web.multipart.MultipartFile;

public interface SignupCommand {
    Member signup(SignupRequest signupRequest, MultipartFile file);
}
