package hu.isakots.user;

import hu.isakots.user.domain.UserEntity;
import hu.isakots.errorhandling.domain.ApplicationException;
import hu.isakots.errorhandling.domain.ErrorCodeEnum;
import hu.isakots.user.domain.User;
import hu.isakots.user.port.UserDaoPort;
import org.springframework.stereotype.Component;

@Component
public class UserDao implements UserDaoPort {

    private final UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.userName());
        userEntity = userRepository.save(userEntity);
        return new User(userEntity.getId(), userEntity.getUsername());
    }

    @Override
    public User retrieveUserById(Long id) {
        return userRepository.findById(id)
            .map(userEntity -> new User(userEntity.getId(), userEntity.getUsername()))
            .orElseThrow(() -> new ApplicationException(ErrorCodeEnum.USER_NOT_FOUND, String.format("User was not found with id: %s", id)));
    }

}
