package cbnu.io.cbnuswalrami.test.helper.fixture;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Password;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Role;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.StudentNumber;

public class MemberFixture {

    public static Member createMember() {
        return new Member(
                LoginId.from("abcd1234"),
                Password.from("SjSj1234!@"),
                StudentNumber.from(2020110110),
                "www.abcd.ac.kr"
        );
    }

    public static Member createAdminMember() {
        return new Member(
                LoginId.from("abcd9999"),
                Password.from("Ddas1212@1"),
                StudentNumber.from(2020110110),
                "www.abc.ac.kr",
                Role.ADMIN
        );
    }
}
