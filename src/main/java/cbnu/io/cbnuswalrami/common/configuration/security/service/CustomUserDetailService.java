package cbnu.io.cbnuswalrami.common.configuration.security.service;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.values.LoginId;
import cbnu.io.cbnuswalrami.business.core.domon.member.infrastructure.command.MemberJpaRepository;
import cbnu.io.cbnuswalrami.business.core.error.CommonTypeException;
import cbnu.io.cbnuswalrami.common.configuration.security.AccountAdapter;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    public CustomUserDetailService(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username: {}", username);
        Member member = memberJpaRepository.findByLoginId(LoginId.from(username))
                .orElseThrow(() -> CbnuException.of(CommonTypeException.NOT_FOUNT_USER));
        return new AccountAdapter(member);
    }
}
