package cbnu.io.cbnuswalrami.business.web.user.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Users;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Password;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.StudentNumber;
import cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.UserJpaRepository;
import cbnu.io.cbnuswalrami.business.web.s3.application.S3Command;
import cbnu.io.cbnuswalrami.business.web.user.application.SignupCommand;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.SignupRequest;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static cbnu.io.cbnuswalrami.business.core.error.CommonTypeException.*;

@Service
public class SignupService implements SignupCommand {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    private final S3Command s3Command;

    public SignupService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder, S3Command s3Command) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Command = s3Command;
    }

    public Users signup(SignupRequest signupRequest, MultipartFile file) {
        System.out.println("============1");

        signupValidate(signupRequest);

        String url = s3Command.uploadFile(file);
        System.out.println("============2");


        Users users = new Users(
                LoginId.from(signupRequest.getLoginId()),
                Password.from(signupRequest.getPassword()),
                StudentNumber.from(signupRequest.getStudentNumber()),
                url
        );

        return userJpaRepository.save(users);
    }

    private void signupValidate(SignupRequest signupRequest) {
        if (signupRequest == null) {
            throw CbnuException.of(NULL_EXCEPTION);
        }

        Optional<Users> isExist = userJpaRepository.findByLoginId(LoginId.from(signupRequest.getLoginId()));
        if (isExist.isPresent()) {
            throw CbnuException.of(EXIST_USER);
        }

        Optional<Users> byStudentNumber = userJpaRepository.findByStudentNumber(StudentNumber.from(signupRequest.getStudentNumber()));
        if (byStudentNumber.isPresent()) {
            throw CbnuException.of(EXIST_USER);
        }
    }
}
