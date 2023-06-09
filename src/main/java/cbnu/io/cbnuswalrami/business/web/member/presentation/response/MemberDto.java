package cbnu.io.cbnuswalrami.business.web.member.presentation.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private Long id;

    private String nickname;

    private Integer studentNumber;

    @QueryProjection
    public MemberDto(Long id, String nickname, Integer studentNumber) {
        this.id = id;
        this.nickname = nickname;
        this.studentNumber = studentNumber;
    }
}
