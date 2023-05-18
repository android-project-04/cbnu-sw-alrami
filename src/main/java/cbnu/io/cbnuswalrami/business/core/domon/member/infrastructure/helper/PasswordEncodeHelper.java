package cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeHelper {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
