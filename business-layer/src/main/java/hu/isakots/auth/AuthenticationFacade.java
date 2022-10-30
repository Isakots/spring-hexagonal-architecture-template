package hu.isakots.auth;

import hu.isakots.auth.domain.AuthContext;
import hu.isakots.auth.domain.LoginParameters;
import hu.isakots.auth.port.AuthenticationFacadePort;
import hu.isakots.user.UserService;
import hu.isakots.user.domain.User;

public class AuthenticationFacade implements AuthenticationFacadePort {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public AuthenticationFacade(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Override
    public String authenticate(LoginParameters loginParameters) {
        User newUser = userService.createUser(loginParameters.username());
        return jwtProvider.generateJwtToken(new AuthContext(newUser.id(), newUser.userName()));
    }

}
