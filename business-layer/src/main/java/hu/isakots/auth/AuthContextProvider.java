package hu.isakots.auth;

import hu.isakots.auth.domain.AuthContext;

public interface AuthContextProvider {
    AuthContext getAuthContext();
}
