package cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ResponseCommunityBookmark {

    private Long id;
    private String title;
    private String description;
    private String url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime createdAt;

    private Long count;

    @QueryProjection
    public ResponseCommunityBookmark(
            Long id,
            String title,
            String description,
            String url,
            ZonedDateTime createdAt,
            Long count
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.createdAt = createdAt;
        this.count = count;
    }

    @Override
    public String toString() {
        return "ResponseCommunityBookmark{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                ", count=" + count +
                '}';
    }
}
