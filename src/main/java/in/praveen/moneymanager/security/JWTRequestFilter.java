package in.praveen.moneymanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTRequestFilter {

    private final UserDetailsService userDetailsService;
}
