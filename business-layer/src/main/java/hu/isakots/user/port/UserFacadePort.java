package hu.isakots.user.port;

import hu.isakots.user.domain.User;

public interface UserFacadePort {
    User retrieveUserById(Long id);
}
