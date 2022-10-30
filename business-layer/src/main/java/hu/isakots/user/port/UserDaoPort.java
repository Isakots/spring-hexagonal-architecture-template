package hu.isakots.user.port;

import hu.isakots.user.domain.User;

public interface UserDaoPort {
    User save(User user);

    User retrieveUserById(Long id);
}
