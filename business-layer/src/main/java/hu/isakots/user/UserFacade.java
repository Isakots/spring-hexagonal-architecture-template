package hu.isakots.user;

import hu.isakots.user.domain.User;
import hu.isakots.user.port.UserFacadePort;

public class UserFacade implements UserFacadePort {

    private final UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User retrieveUserById(Long id) {
        return userService.retrieveUserById(id);
    }

}
