package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.config.JwtTokenProvider;
import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.exception.ForbiddenException;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.model.AuthRequest;
import com.example.universityscheduler.model.AuthResponse;
import com.example.universityscheduler.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAccountRepository userRepo;
    //private final UserAccountService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public boolean existsEmail(String email) {
        log.trace("Check login {} for uniq", email);
        return userRepo.existsByEmail(email);
    }

    @Transactional
    public void registerUser(UserAccount user) {
        log.info("Begin user {} registration", user.getEmail());
        if (existsEmail(user.getEmail())) {
            throw new ForbiddenException();
        }
        userRepo.save(user);
        log.info("User {} has been successfully registered", user.getEmail());
    }

    @Transactional
    public AuthResponse login(AuthRequest login) {
        log.info("START login for user {}", login.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.createToken(authentication);
        if (token == null) {
            throw new NotFoundException();
        }
        return new AuthResponse().token(token);
    }
}
 