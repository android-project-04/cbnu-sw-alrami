package cbnu.io.cbnuswalrami.test.helper.fixture;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.*;

public class MemberFixture {

    public static Member createMember() {
        return new Member(
                LoginId.from("abcd1234"),
                Password.from("SjSj1234!@"),
                Nickname.from("히어로123"),
                StudentNumber.from(2020110110),
                "www.abcd.ac.kr"
        );
    }

    public static Member createAdminMember() {
        return new Member(
                LoginId.from("abcd9999"),
                Password.from("Ddas1212@1"),
                Nickname.from("어드민123"),
                StudentNumber.from(2020110110),
                "www.abc.ac.kr",
                Role.ADMIN
        );
    }
}
