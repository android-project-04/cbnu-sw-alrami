package cbnu.io.cbnuswalrami.business.web.community.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommunity {

    @NotNull(message = "제목을 입력해주세요.")
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "게시글의 글을 작성해주세요.")
    @NotEmpty(message = "게시글의 글을 작성해주세요.")
    private String description;
}
