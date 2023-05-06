package cbnu.io.cbnuswalrami.common.configuration.security;

import cbnu.io.cbnuswalrami.business.core.domon.user.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.user.entity.values.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.Set;

public class AccountAdapter extends User {

    public AccountAdapter(Member member) {
        super(member.getLoginId().getLoginId(), member.getPassword().getPassword(), authorities(member.getRole()));
    }

    private static Set<SimpleGrantedAuthority> authorities(Role role) {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.toString());
        simpleGrantedAuthorities.add(simpleGrantedAuthority);
        return simpleGrantedAuthorities;
    }
}
