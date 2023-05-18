package cbnu.io.cbnuswalrami.test.helper.fixture.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    public static List<Member> create15NoApprovalMembers() {
        List<Member> members = new ArrayList<>();

        IntStream.rangeClosed(1, 15)
                .forEach(x -> members.add(
                        new Member(
                                LoginId.from("abcd2323" + x),
                                Password.from("Abcd1234@!" + x),
                                Nickname.from("nickname" + x),
                                StudentNumber.from(2020110110 + x),
                                "www.abc.kr"
                        )
                ));
        return members;
    }
}
