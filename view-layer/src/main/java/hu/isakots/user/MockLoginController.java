package hu.isakots.user;

import hu.isakots.auth.domain.LoginParameters;
import hu.isakots.auth.port.AuthenticationFacadePort;
import hu.isakots.user.domain.LoginResponse;
import hu.isakots.user.domain.MockLoginRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile({"local", "dev"})
public class MockLoginController {

    private final AuthenticationFacadePort authenticationFacadePort;

    public MockLoginController(AuthenticationFacadePort authenticationFacadePort) {
        this.authenticationFacadePort = authenticationFacadePort;
    }

    @PostMapping("/login/mock")
    public LoginResponse mockLogin(@RequestBody MockLoginRequest loginRequest) {
        LoginParameters loginParameters = new LoginParameters(loginRequest.username());
        String accessToken = authenticationFacadePort.authenticate(loginParameters);
        return new LoginResponse(accessToken);
    }

}
