package hu.isakots.config.security.filter;


import hu.isakots.auth.JwtProvider;
import hu.isakots.auth.domain.AuthContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static hu.isakots.auth.domain.SecurityConstants.ACCESS_TOKEN_HEADER_LEADING;
import static hu.isakots.auth.domain.SecurityConstants.ACCESS_TOKEN_HEADER_NAME;

public class AccessTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenFilter.class);

    private final JwtProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public AccessTokenFilter(JwtProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        try {
            String jwt = extractAccessToken(request);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                AuthContext user = tokenProvider.getUserFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(user.userId().toString());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred during authentication: ", e);
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader(ACCESS_TOKEN_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith(ACCESS_TOKEN_HEADER_LEADING)) {
            return authHeader.replace(ACCESS_TOKEN_HEADER_LEADING, "");
        }

        return null;
    }
}
