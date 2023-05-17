package cbnu.io.cbnuswalrami.test.helper.fixture;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.user.application.ApprovalChangeCommand;
import cbnu.io.cbnuswalrami.business.web.user.application.SignupCommand;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.SignupRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class SignupFixture {
    private final SignupCommand signupCommand;
    private final ApprovalChangeCommand approvalChangeCommand;

    private final MemberJpaRepository memberJpaRepository;

    public SignupFixture(
            SignupCommand signupCommand,
            ApprovalChangeCommand approvalChangeCommand,
            MemberJpaRepository memberJpaRepository
    ) {
        this.signupCommand = signupCommand;
        this.approvalChangeCommand = approvalChangeCommand;
        this.memberJpaRepository = memberJpaRepository;
    }

    public Member signupMember(String loginId, String password, String nickname) {
        SignupRequest signupRequest = new SignupRequest(
                loginId,
                password,
                nickname,
                2020110110
        );
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );
        Member signupMember = signupCommand.signup(signupRequest, multipartFile);
        approvalChangeCommand.changeApproval(signupMember.getId());
        return signupMember;
    }

    public Member signupNonApprovalMember(String loginId, String password, String nickname) {
        SignupRequest signupRequest = new SignupRequest(
                loginId,
                password,
                nickname,
                2020110110
        );
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );
        Member signupMember = signupCommand.signup(signupRequest, multipartFile);
        return signupMember;
    }

    public List<Member> signup15NonApprovalMembers() {
        List<Member> noApprovalMembers = MemberFixture.create15NoApprovalMembers();
            return memberJpaRepository.saveAll(noApprovalMembers);
    }
}
