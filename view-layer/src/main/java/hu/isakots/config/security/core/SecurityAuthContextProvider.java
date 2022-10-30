package hu.isakots.config.security.core;

import hu.isakots.auth.AuthContextProvider;
import hu.isakots.auth.domain.AuthContext;
import hu.isakots.config.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class SecurityAuthContextProvider implements AuthContextProvider {

    @Override
    public AuthContext getAuthContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getAuthContext();
        } else {
            throw new IllegalStateException("Authentication principal was not found or malformed!");
        }
    }

}
