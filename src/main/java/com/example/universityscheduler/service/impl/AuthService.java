package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.exception.BadAuthorizeException;
import com.example.universityscheduler.exception.ConflictException;
import com.example.universityscheduler.exception.ForbiddenException;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.AuthMapper;
import com.example.universityscheduler.model.AuthRequest;
import com.example.universityscheduler.model.AuthResponse;
import com.example.universityscheduler.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.AdapterUtils;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.authorization.client.AuthorizationDeniedException;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final KeycloakSpringBootProperties keycloakConfig;
    private final UserAccountRepository userRepo;
 
    private final Keycloak keycloak;
    private final UserAccountService userService;
    private final AuthzClient authzClient;
    private final KeycloakDeployment deployment;

    private static final String TOKEN_ENDPOINT = "/protocol/openid-connect/token";

    @Transactional
    public boolean existsEmail(String email) {
        log.trace("Check login {} for uniq", email);
        return userRepo.existsByEmail(email);
    }

    @Transactional
    public AuthResponse registerUser(UserAccount user, AuthRequest request) {
        log.info("Begin user {} registration", user.getEmail());
 
        if (existsEmail(user.getEmail())) {
            throw new ForbiddenException();
        }
        val userSaved = userRepo.save(user);
 
        log.info("User {} has been successfully registered", user.getEmail());
        return createKeycloakUser(request, userSaved);
    }

    @Transactional
    public AuthResponse createKeycloakUser(AuthRequest request, UserAccount savedUser) {
        log.info("RUN registration for mail {}", request.getEmail());
 
        val userRepresentation = AuthMapper.INSTANCE.mapUserRepresentation(request);
        val usersResource = keycloak.realm(keycloakConfig.getRealm()).users();
        val response = usersResource.create(userRepresentation);
        switch (response.getStatus()) {
            case 201:
                log.info("user has been successfully created");
                break;
            case 409:
                log.error("conflict with users' params: [{}]", response);
                throw new ConflictException();
            default:
                log.error("Some error occurred during client registration: {} - {}",
                        response.getStatus(), response.getStatusInfo());
                throw new RuntimeException();
        }
        userRepo.save(savedUser);
 
        log.info("FINISH registration process has been successfully completed");
        return login(request);
    }

    @Transactional
    public AuthResponse login(AuthRequest login) {
        log.info("START login for user {}", login.getEmail());

        try {
            val response = authzClient.authorization(login.getEmail(), login.getPassword())
                    .authorize();

            UserAccount user = userRepo.findByEmail(login.getEmail());
            checkUser(user);

            val result = new AuthResponse()
                    .tokenType(response.getTokenType())
                    .token(response.getToken())
                    .refreshToken(response.getRefreshToken());
 
            log.info("FINISH login for user {} successfully", login.getEmail());
            return result;
        } catch (NotFoundException ex) {
            userRepo.save(AuthMapper.INSTANCE.map(login));
            return login(login);
        } catch (AuthorizationDeniedException | HttpResponseException ex) {
            log.debug("Exception when login {}", login.getEmail(), ex);
            log.error("FINISH login for user {} is bad", login.getEmail());
            throw new BadAuthorizeException();
        } catch (RuntimeException ex) {
            log.error(
                    String.format("Internal logic error occurred during login for [%s]", login.getEmail()),
                    ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Some error occurred during login", ex);
            throw new BadAuthorizeException();
        }
    }

    @Transactional
    public AuthResponse refreshToken(String refresh) {
        log.info("START refreshing token");
        try {
            val keycloakConf = authzClient.getConfiguration();
            String url = keycloakConf.getAuthServerUrl()
                    + "/realms/"
                    + keycloakConf.getRealm() + TOKEN_ENDPOINT;
 
            String clientId = keycloakConf.getResource();
            String secret = (String) keycloakConf.getCredentials().get("secret");
            val http = new Http(authzClient.getConfiguration(), (params, headers) -> {
            });
 
            val response = http.<AccessTokenResponse>post(url)
                    .authentication()
                    .client()
                    .form()
                    .param("grant_type", "refresh_token")
                    .param("refresh_token", refresh)
                    .param("client_id", clientId)
                    .param("client_secret", secret)
                    .response()
                    .json(AccessTokenResponse.class)
                    .execute();
 
            val result = new AuthResponse()
                    .tokenType(response.getTokenType())
                    .token(response.getToken())
                    .refreshToken(response.getRefreshToken());
            log.info("FINISH tokenRefresh");
            return result;
        } catch (AuthorizationDeniedException | HttpResponseException ex) {
            log.debug("Exception when tokenRefresh", ex);
            log.info("FAIL cannot refresh token");
            throw new BadAuthorizeException();
        }
    }

    public void logout() {
        log.info("RUN logout");
        val token = userService.getToken();
        logout(token);
    }

    private void logout(AccessToken token) {
        try {
            val state = Optional.ofNullable(token)
                    .map(AccessToken::getSessionState)
                    .orElseThrow(javax.ws.rs.NotFoundException::new);
 
            keycloak.realm(keycloakConfig.getRealm()).deleteSession(state);
        } catch (javax.ws.rs.NotFoundException ex) {
            log.info("Not found session for keycloak");
        }
        log.info("Successfully logged out");
    }

    private void checkUser(UserAccount uncheckedUser) {
        Optional.ofNullable(uncheckedUser)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteUserCompletely(String mail) {
        Optional.ofNullable(mail)
                .filter(m -> !StringUtils.isBlank(m))
                .orElseThrow(NotFoundException::new);
 
        log.debug("User with email {} requested deletion of user with email {}",
                userService.getCurrentUserAccount().getEmail(),
                mail);
 
        val user = userRepo.findByEmail(mail);
        Optional.ofNullable(user).ifPresentOrElse(this::deleteUserCompletely, NotFoundException::new);
 
        log.debug("User with email {} was deleted", mail);
    }

    private void deleteUserCompletely(UserAccount user) {
        log.info("RUN removing user with all his data");
        userRepo.delete(user);
        try {
            val userResource = getKeycloakUser(user.getEmail());
            userResource.remove();
        } catch (NotFoundException ex) {
            log.error("There is no {} user in keycloak", user.getEmail());
        }
 
        log.info("FINISH user {} has been removed", user.getEmail());
    }


    private UserResource getKeycloakUser(String email) throws NotFoundException {
        val userResource = keycloak.realm(keycloakConfig.getRealm()).users();
        val foundUsers = userResource.search(email, true);
 
        if (CollectionUtils.isEmpty(foundUsers)) {
            log.error("No keycloak user has been found for {}", email);
            throw new NotFoundException();
        }
 
        if (foundUsers.size() > 1) {
            log.error("Found more than one keycloak user while the only was expected");
            throw new ConflictException();
        }
 
        val found = foundUsers.iterator().next();
        return Optional.of(userResource.get(found.getId()))
                .orElseThrow(ConflictException::new);
    }

    public Authentication authenticateByToken(String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        AccessToken accessToken = userService.getToken(token);
        log.debug("received token:{} for userRegId: {}, isActive: {}",
                bearerToken.substring(bearerToken.length() - 4),
                accessToken.getSubject(),
                accessToken.isActive());
 
        if (accessToken.isActive()) {
            val roles = Optional.ofNullable(accessToken.getRealmAccess())
                    .map(AccessToken.Access::getRoles)
                    .orElse(Collections.emptySet());
 
            val session = new RefreshableKeycloakSecurityContext(deployment, null, token, accessToken, null, null, null);
            val principal = new KeycloakPrincipal<>(AdapterUtils.getPrincipalName(deployment, accessToken), session);
            val keycloakAccount = new SimpleKeycloakAccount(principal, roles, session);
 
            return new KeycloakAuthenticationProvider().authenticate(new KeycloakAuthenticationToken(keycloakAccount, false));
        } else {
            throw new RuntimeException("Invalid bearer token");
        }
    }
}
 