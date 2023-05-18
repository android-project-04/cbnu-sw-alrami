package cbnu.io.cbnuswalrami.business.web.notification.presentation.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDto {

    private Long id;

    private String title;

    private String url;

    @QueryProjection
    public NotificationDto(Long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }
}
