package hu.isakots.config.security.core;

import hu.isakots.auth.domain.AuthContext;
import hu.isakots.config.security.domain.CustomUserDetails;
import hu.isakots.errorhandling.domain.ApplicationException;
import hu.isakots.errorhandling.domain.ErrorCodeEnum;
import hu.isakots.user.domain.User;
import hu.isakots.user.port.UserFacadePort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserFacadePort userFacadePort;

    public CustomUserDetailsService(UserFacadePort userFacadePort) {
        this.userFacadePort = userFacadePort;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user = userFacadePort.retrieveUserById(Long.valueOf(userId));

        if (user == null) {
            throw new ApplicationException(ErrorCodeEnum.USER_NOT_FOUND, String.format("User was not found with id: %s", userId));
        }

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setAuthContext(new AuthContext(user.id(), user.userName()));
        return customUserDetails;
    }

}
