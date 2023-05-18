package cbnu.io.cbnuswalrami.business.web.member.application.service.paging;

import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.query.MemberQueryRepository;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalCursorPagingService {

    private final MemberQueryRepository memberQueryRepository;

    @Transactional
    public CursorResult findMemberDtosByCursor(Cursor cursor) {

        List<MemberDto> memberDtos = getMemberDtos(cursor);
        Long lastIndex = memberDtos.isEmpty() ? 0 : memberDtos.get(memberDtos.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex);

        return CursorResult.from(memberDtos, hasNext, lastIndex);
    }

    private List<MemberDto> getMemberDtos(Cursor cursor) {
        if (cursor.getNext() == null) {
            return memberQueryRepository.findMemberDtosByCursor(cursor.getSize());
        }
        return memberQueryRepository.findMemberDtosByCursor(cursor.getSize(), cursor.getNext());
    }

    private Boolean hasNext(Long id) {
        if (id == null || id <= 1) return false;

        return memberQueryRepository.existMemberById(id);
    }
}
