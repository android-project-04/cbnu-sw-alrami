package cbnu.io.cbnuswalrami.test.integration.signup;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Users;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Role;
import cbnu.io.cbnuswalrami.business.core.domon.user.infrastructure.UserJpaRepository;
import cbnu.io.cbnuswalrami.business.web.user.application.SignupCommand;
import cbnu.io.cbnuswalrami.business.web.user.presentation.request.SignupRequest;
import cbnu.io.cbnuswalrami.common.configuration.container.DatabaseTestBase;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import cbnu.io.cbnuswalrami.test.helper.fixture.UserFixture;
import cbnu.io.cbnuswalrami.test.helper.fake.s3.S3Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Import(S3Configuration.class)
public class SignupIntegrationTest extends DatabaseTestBase {

    @Autowired
    private SignupCommand signupCommand;

    @Autowired
    private UserJpaRepository userJpaRepository;


    @DisplayName("올바르지않은 패스워드로 회원가입하면 예외를 반환한다.")
    @Test
    public void given_invalid_password_when_signup_then_ex() {
        // given
        String loginId = "xxx123123";
        String password = "cd1234";
        Integer studentNumber = 2020000111;
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );


        // then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> signupCommand.signup(new SignupRequest(loginId, password, studentNumber), multipartFile));

        assertEquals("올바른 비밀번호를 입력해주세요.", exception.getMessage());

    }

    @DisplayName("올바르지않은 학번으로 회원가입하면 예외를 반환한다.")
    @Test
    public void given_invalid_student_number_when_signup_then_ex() {
        // given
        String loginId = "abcd1234";
        String password = "Abcd1234@!";
        Integer studentNumber = 202011011;
        String pictureUrl = "abc.ac.kr";
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );

        // then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> signupCommand.signup(new SignupRequest(loginId, password, studentNumber), multipartFile));
        assertEquals("10글자의 학번을 입력해주세요.", exception.getMessage());
    }

    @DisplayName("이미 존재하는 유저이면 예외를 반환한다.")
    @Test
    public void given_exist_user_when_signup_then_ex() {
        // given
        String pictureUrl = "abc.ac.kr";
        Users user = UserFixture.createUser();
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );
        userJpaRepository.save(user);
        SignupRequest signupRequest = new SignupRequest(
                user.getLoginId().getLoginId(),
                user.getPassword().getPassword(),
                2020110110
        );

        // then
        CbnuException cbnuException = assertThrows(CbnuException.class, () -> signupCommand.signup(signupRequest, multipartFile));
        assertEquals("이미 존재하는 유저입니다.", cbnuException.getMessage());
    }

    @DisplayName("이미 존재하는 학번이면 예외를 반환한다.")
    @Test
    public void given_exist_student_number_when_signup_then_ex() {
        // given
        Users user = UserFixture.createUser();
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );        SignupRequest signupRequest = new SignupRequest(
                "aaaaa123",
                "Sqwer123@@@",
                user.getStudentNumber().getStudentNumber()
        );

        userJpaRepository.save(user);

        // when

        // then
        CbnuException exception = assertThrows(
                CbnuException.class,
                () -> signupCommand.signup(signupRequest, multipartFile));
        assertEquals("이미 존재하는 유저입니다.", exception.getMessage());

    }

    @DisplayName("처음 가입하면 NOMAL이라는 권한을 갖는다.")
    @Test
    public void given_valid_user_when_signup_then_normal_grant() {
        // given
        Users user = UserFixture.createUser();
        SignupRequest signupRequest = new SignupRequest(
                user.getLoginId().getLoginId(),
                user.getPassword().getPassword(),
                user.getStudentNumber().getStudentNumber()
        );
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );


        // when
        Users signup = signupCommand.signup(signupRequest, multipartFile);

        // then
        assertEquals(Role.NORMAL, signup.getRole());

    }

    @DisplayName("올바른 유저 정보로 회원가입하면 user를 반환한다.")
    @Test
    public void given_valid_user_info_when_signup_then_return_user_id() {
        // given
        String loginId = "xxx123123";
        String password = "Sbcd1234@@!!";
        Integer studentNumber = 2020000111;
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World".getBytes()
        );


        // when - 회원가입
        Users signupUsers = signupCommand.signup(new SignupRequest(loginId, password, studentNumber), multipartFile);

        // then - userId == 1
        assertThat(signupUsers).isNotNull();
    }
}