package cbnu.io.cbnuswalrami.business.web.member.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ChangeNicknameService {

    private final MemberJpaRepository memberJpaRepository;

    public MemberDto changeNickname(
            String changeNickname,
            Member member
    ){
        // 닉네임 변경 가능한지 검증(날짜) 검사
        validateLastModifiedDate(member.getLastModifiedAt());

        member.changeNickname(changeNickname);
        memberJpaRepository.save(member);

        MemberDto memberDto = new MemberDto(
                member.getId(),
                changeNickname,
                member.getStudentNumber().getStudentNumber()
        );

        return memberDto;
    }

    private void validateLastModifiedDate(LocalDateTime memberLastModifiedDate) {
        LocalDateTime now = LocalDateTime.now();
        long dayBetween = ChronoUnit.DAYS.between(memberLastModifiedDate, now);

        System.out.println("memberLastModifiedDate = " + memberLastModifiedDate);
        System.out.println("dayBetween = " + dayBetween);

        if (dayBetween < 30) {
            throw new IllegalArgumentException("아직 마지막으로 유저정보를 바꾼지 30일이 지나지 않았습니다.");
        }
    }
}
