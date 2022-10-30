package hu.isakots.auth.domain;

public record AuthContext(
    Long userId,
    String username
) {

}
