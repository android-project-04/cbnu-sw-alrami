package cbnu.io.cbnuswalrami.test.helper.fixture;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Users;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Password;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.StudentNumber;

public class UserFixture {


    public static Users createUser() {
        return new Users(
                LoginId.from("abcd1234"),
                Password.from("SjSj1234!@"),
                StudentNumber.from(2020110110),
                "www.abcd.ac.kr"
        );
    }
}
