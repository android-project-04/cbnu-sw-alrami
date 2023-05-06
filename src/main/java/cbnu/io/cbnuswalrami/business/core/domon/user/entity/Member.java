package cbnu.io.cbnuswalrami.business.core.domon.user.entity;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Embedded
    @Column(name = "login_id")
    private LoginId loginId;

    @Embedded
    @Column(name = "password")
    private Password password;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @Embedded
    @Column(name = "student_number")
    private StudentNumber studentNumber;

    @Column(name = "user_picture_url")
    private String userPictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_deleted")
    private Deleted isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval")
    private Approval approval;



    public Member(LoginId loginId, Password password, StudentNumber studentNumber, String userPictureUrl, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.studentNumber = studentNumber;
        this.userPictureUrl = userPictureUrl;
        this.role = role == null ? Role.NORMAL : role;
        this.isDeleted = Deleted.FALSE;
    }

    public Member(LoginId loginId, Password password, StudentNumber studentNumber, String userPictureUrl) {
        this.loginId = loginId;
        this.password = password;
        this.studentNumber = studentNumber;
        this.userPictureUrl = userPictureUrl;
        this.role = Role.NORMAL;
        this.isDeleted = Deleted.FALSE;
        this.approval = Approval.NO;
    }

    public void changeApprovalToOk() {
        this.approval = Approval.OK;
    }

    public Long getId() {
        return id;
    }

    public LoginId getLoginId() {
        return loginId;
    }

    public Password getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    public Role getRole() {
        return role;
    }

    public Deleted getIsDeleted() {
        return isDeleted;
    }

    public Approval getApproval() {
        return approval;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return Objects.equals(id, member.id) &&
                Objects.equals(loginId, member.loginId) &&
                Objects.equals(password, member.password) &&
                Objects.equals(createdAt, member.createdAt) &&
                Objects.equals(lastModifiedAt, member.lastModifiedAt) &&
                Objects.equals(studentNumber, member.studentNumber) &&
                Objects.equals(userPictureUrl, member.userPictureUrl) &&
                role == member.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginId, password, createdAt, lastModifiedAt, studentNumber, userPictureUrl, role);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
