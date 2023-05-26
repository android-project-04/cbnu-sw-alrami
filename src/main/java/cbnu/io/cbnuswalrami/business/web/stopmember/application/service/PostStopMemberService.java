package cbnu.io.cbnuswalrami.business.web.stopmember.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Role;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.stopmember.entity.StopMember;
import cbnu.io.cbnuswalrami.business.core.domon.stopmember.infrastructure.command.StopMemberJpaRepository;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.stopmember.presentation.response.ResponseStopMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Role.*;

@Service
@RequiredArgsConstructor
public class PostStopMemberService {

    private final MemberJpaRepository memberJpaRepository;

    private final StopMemberJpaRepository stopMemberJpaRepository;

    @Transactional
    public ResponseStopMember createStopMember(RequestId memberId) {
        Member member = getMember(memberId);
        member.changeRole(STOP_USER);

        StopMember stopMember = new StopMember(member);
        stopMemberJpaRepository.save(stopMember);

        ResponseStopMember responseStopMember = new ResponseStopMember(
                member.getId(),
                member.getNickname().getNickname(),
                member.getRole(),
                stopMember.getLastStopDay()
        );
        return responseStopMember;
    }

    private Member getMember(RequestId memberId) {
        Member member = memberJpaRepository.findById(memberId.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버으 id값입니다."));
        return member;
    }
}
