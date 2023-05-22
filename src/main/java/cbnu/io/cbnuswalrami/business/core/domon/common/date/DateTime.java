package cbnu.io.cbnuswalrami.business.core.domon.common.date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class DateTime {

    @Column(name = "created_at")
    ZonedDateTime createdAt;

    @Column(name = "last_modified_at")
    ZonedDateTime lastModifiedAt;


    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        lastModifiedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public ZonedDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
