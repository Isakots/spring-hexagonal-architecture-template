package hu.isakots.config;

import hu.isakots.auth.AuthenticationFacade;
import hu.isakots.auth.JwtProvider;
import hu.isakots.auth.port.AuthenticationFacadePort;
import hu.isakots.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeanConfiguration {

    @Bean
    AuthenticationFacadePort authenticationFacadePort(JwtProvider jwtProvider, UserService userService) {
        return new AuthenticationFacade(jwtProvider, userService);
    }

}
