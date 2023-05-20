package cbnu.io.cbnuswalrami.business.web.community.presentation.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ResponseCommunity {
    private Long id;
    private String title;
    private String description;
    private String url;

    @QueryProjection
    public ResponseCommunity(Long id, String title, String description, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ResponseCommunity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
