package hu.isakots.auth.port;

import hu.isakots.auth.domain.LoginParameters;

public interface AuthenticationFacadePort {
    String authenticate(LoginParameters loginParameters);
}
