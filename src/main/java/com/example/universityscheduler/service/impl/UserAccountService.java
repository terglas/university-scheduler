//package com.example.universityscheduler.service.impl;
//
//import com.example.universityscheduler.domain.UserAccount;
//import com.example.universityscheduler.exception.BadAuthorizeException;
//import com.example.universityscheduler.exception.ForbiddenException;
//import com.example.universityscheduler.exception.NotFoundException;
//import com.example.universityscheduler.repository.UserAccountRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class UserAccountService {
//    private final UserAccountRepository userRepo;
//
//    public UserAccount getCurrentUserAccount() {
//        final var username = getUserAccountName();
//        return Optional.of(username)
//                .map(userRepo::findByEmail)
//                .orElseThrow(() -> {
//                    log.warn("Not found user {} in the service", username);
//                    return new ForbiddenException();
//                });
//    }
//
//    public String getUserAccountName() {
//       final var token = getToken();
//        return Optional.of(token)
//                .map(AccessToken::getPreferredUsername)
//                .orElseThrow(() -> {
//                    log.warn("Error token is bad preferred_username token_id={}, subject={}",
//                            token.getId(), token.getSubject());
//                    return new BadAuthorizeException();
//                });
//    }
//
//    public AccessToken getToken() {
//        final var context = SecurityContextHolder.getContext().getAuthentication();
//        return Optional.ofNullable(context)
//                .filter(KeycloakAuthenticationToken.class::isInstance)
//                .map(KeycloakAuthenticationToken.class::cast)
//                .map(KeycloakAuthenticationToken::getAccount)
//                .map(OidcKeycloakAccount::getKeycloakSecurityContext)
//                .map(KeycloakSecurityContext::getToken)
//                .orElseThrow(BadAuthorizeException::new);
//    }
//
//    public AccessToken getToken(String token) {
//        try {
//            return TokenVerifier.create(token, AccessToken.class).getToken();
//        } catch (VerificationException ex) {
//            log.error("Error occurred during jwt mapping to AccessToken", ex);
//            return null;
//        }
//    }
//
//    public UserAccount findByEmail(String email) {
//        UserAccount
//                user = userRepo.findByEmail(email);
//        if (user != null) {
//            return user;
//        }
//        throw new NotFoundException("UserAccount " + email + " not found");
//    }
//}
