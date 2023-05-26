package cbnu.io.cbnuswalrami.business.web.stopmember.presentation.response;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ResponseStopMember {

    private Long id;

    private String nickname;

    private Role role;

    //
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime lastStopDay;
}
