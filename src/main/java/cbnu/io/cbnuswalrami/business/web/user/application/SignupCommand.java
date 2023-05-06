package cbnu.io.cbnuswalrami.business.web.user.application;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.SignupRequest;
import org.springframework.web.multipart.MultipartFile;

public interface SignupCommand {
    Member signup(SignupRequest signupRequest, MultipartFile file);
}
