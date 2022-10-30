package hu.isakots.config;

import hu.isakots.user.UserDao;
import hu.isakots.user.UserRepository;
import hu.isakots.user.domain.UserEntity;
import hu.isakots.user.UserFacade;
import hu.isakots.user.UserService;
import hu.isakots.user.port.UserDaoPort;
import hu.isakots.user.port.UserFacadePort;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EntityScan(basePackageClasses = UserEntity.class)
public class UserBeanConfiguration {

    @Bean
    UserFacadePort userFacadePort(UserService userService) {
        return new UserFacade(userService);
    }

    @Bean
    UserDaoPort userDaoPort(UserRepository userRepository) {
        return new UserDao(userRepository);
    }

}
