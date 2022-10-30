package hu.isakots.user.domain;

import java.io.Serializable;

public record MockLoginRequest(
    String username
) implements Serializable {
}
