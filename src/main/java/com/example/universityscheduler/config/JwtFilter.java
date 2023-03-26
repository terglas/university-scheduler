package com.example.universityscheduler.config;

import com.example.universityscheduler.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie token = WebUtils.getCookie(request,"accessToken");

        if (token != null) {
            try {
                log.debug(
                        "Attempt to authenticate by token [{}]",
                        token.getValue().substring(token.getValue().length() - 4));
                final var authentication = authService.authenticateByToken(token.getValue());

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            } catch (RuntimeException e) {
                log.error("Error occurred during JWT token checking", e);
                SecurityContextHolder.clearContext();
                throw e;
            }
        } else {
            log.debug("Received token is null");
        }

        try {
            filterChain.doFilter(request, response);
        } catch (NullPointerException npe) {
            log.error("NPE happened", npe);
        }
    }
}
