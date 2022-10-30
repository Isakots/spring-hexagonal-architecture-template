package hu.isakots.user;

import hu.isakots.user.domain.User;
import hu.isakots.user.port.UserDaoPort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDaoPort userDaoPort;

    public UserService(UserDaoPort userDaoPort) {
        this.userDaoPort = userDaoPort;
    }

    public User createUser(String username) {
        return userDaoPort.save(new User(null, username));
    }

    public User retrieveUserById(Long id) {
        return userDaoPort.retrieveUserById(id);
    }
}
