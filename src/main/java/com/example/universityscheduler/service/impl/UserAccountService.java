package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.exception.BadAuthorizeException;
import com.example.universityscheduler.exception.ForbiddenException;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userRepo;

    public UserAccount getCurrentUser() {
        final var username = getUserAccountName();
        return Optional.of(username)
                .map(userRepo::findByEmail)
                .orElseThrow(() -> {
                    log.warn("Not found user {} in the service", username);
                    return new ForbiddenException();
                });
    }

    public String getUserAccountName() {
       final var token = getToken();
        return Optional.of(token)
                .map(UserDetails::getUsername)
                .orElseThrow(() -> {
                    log.warn("Not found user in the token");
                    return new BadAuthorizeException("Not found user in the token");
                });
    }

    public UserDetails getToken() {
        final var context = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(context)
                .filter(authentication -> authentication.getPrincipal() instanceof UserDetails)
                .map(authentication -> (UserDetails) authentication.getPrincipal())
                .orElseThrow(() -> {
                    log.warn("Not found user in the token");
                    return new ForbiddenException("Not authorized");
                });
    }

//    public AccessToken getToken(String token) {
//        try {
//            return TokenVerifier.create(token, AccessToken.class).getToken();
//        } catch (VerificationException ex) {
//            log.error("Error occurred during jwt mapping to AccessToken", ex);
//            return null;
//        }
//    }

    public UserAccount findByEmail(String email) {
        UserAccount
                user = userRepo.findByEmail(email);
        if (user != null) {
            return user;
        }
        throw new NotFoundException("UserAccount " + email + " not found");
    }
}
