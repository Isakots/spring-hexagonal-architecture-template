package hu.isakots.auth.domain;

public class SecurityConstants {

    private SecurityConstants() {
        // intentionally left blank
    }

    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 36_000_000; // 1 hour

    public static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";
    public static final String ACCESS_TOKEN_HEADER_LEADING = "Bearer ";
    public static final String TOKEN_CUSTOM_PAYLOAD_KEY = "data";

}
