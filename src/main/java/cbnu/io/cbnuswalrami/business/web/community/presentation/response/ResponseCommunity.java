package cbnu.io.cbnuswalrami.business.web.community.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCommunity {
    private Long id;
    private String title;
    private String description;
    private String url;
}
