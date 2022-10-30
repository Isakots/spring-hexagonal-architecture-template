package hu.isakots.user;

import hu.isakots.user.domain.User;
import hu.isakots.user.domain.UserView;
import hu.isakots.user.port.UserFacadePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserFacadePort userFacadePort;

    public UserController(UserFacadePort userFacadePort) {
        this.userFacadePort = userFacadePort;
    }

    @GetMapping("/users/{id}")
    UserView retrieveUser(@PathVariable Long id) {
        User user = userFacadePort.retrieveUserById(id);
        return new UserView(user.id(), user.userName());
    }

}
