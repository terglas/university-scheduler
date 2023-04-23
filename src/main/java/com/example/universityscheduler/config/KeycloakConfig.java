package com.example.universityscheduler.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(KeycloakSpringBootProperties props) {
        return KeycloakBuilder.builder()
                .serverUrl(props.getAuthServerUrl())
                .realm(props.getRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(props.getResource())
                .clientSecret(props.getCredentials().get("secret").toString())
                .build();
    }

    @Bean
    public AuthzClient keycloakAuthzClient(KeycloakSpringBootProperties props) {
       final var config = new org.keycloak.authorization.client.Configuration(
                props.getAuthServerUrl(), props.getRealm(),
                props.getResource(), props.getCredentials(),
                null);
        return AuthzClient.create(config);
    }

    @Bean
    public KeycloakDeployment deployment(@Autowired(required = false) AdapterConfig adapterConfig) {
        if(adapterConfig != null) {
            return KeycloakDeploymentBuilder.build(adapterConfig);
        } else {
            return null;
        }
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
