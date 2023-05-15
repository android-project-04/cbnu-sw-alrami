package cbnu.io.cbnuswalrami.business.web.user.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Nickname;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Password;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.StudentNumber;
import cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.s3.application.S3Command;
import cbnu.io.cbnuswalrami.business.web.user.application.SignupCommand;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.SignupRequest;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static cbnu.io.cbnuswalrami.business.core.error.CommonTypeException.*;

@Service
public class SignupService implements SignupCommand {

    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;

    private final S3Command s3Command;

    public SignupService(MemberJpaRepository memberJpaRepository, PasswordEncoder passwordEncoder, S3Command s3Command) {
        this.memberJpaRepository = memberJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Command = s3Command;
    }

    @Transactional
    public Member signup(SignupRequest signupRequest, MultipartFile file) {
        signupValidate(signupRequest);

        String url = s3Command.uploadFile(file);


        Member member = new Member(
                LoginId.from(signupRequest.getLoginId()),
                Password.from(signupRequest.getPassword()),
                Nickname.from(signupRequest.getNickname()),
                StudentNumber.from(signupRequest.getStudentNumber()),
                url
        );

        return memberJpaRepository.save(member);
    }

    private void signupValidate(SignupRequest signupRequest) {
        if (signupRequest == null) {
            throw CbnuException.of(NULL_EXCEPTION);
        }

        Optional<Member> isExist = memberJpaRepository.findByLoginId(LoginId.from(signupRequest.getLoginId()));
        if (isExist.isPresent()) {
            throw CbnuException.of(EXIST_USER);
        }

        Optional<Member> byStudentNumber = memberJpaRepository.findByStudentNumber(StudentNumber.from(signupRequest.getStudentNumber()));
        if (byStudentNumber.isPresent()) {
            throw CbnuException.of(EXIST_USER);
        }

        Optional<Member> byNickname = memberJpaRepository.findByNickname(Nickname.from(signupRequest.getNickname()));

        if (byNickname.isPresent()) {
            throw CbnuException.of(EXIST_USER);
        }
    }
}
