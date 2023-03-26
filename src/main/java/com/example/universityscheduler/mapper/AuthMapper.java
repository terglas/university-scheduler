package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.model.AuthRequest;
import org.keycloak.representations.UserInfo;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    UserAccount map(AuthRequest src);

    AuthRequest toLoginRequest(String email, String password);

    UserInfo mapUserInfo(UserAccount src);

    @Mapping(target = "username", source = "email")
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "self", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTimestamp", ignore = true)
    @Mapping(target = "totp", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "credentials", ignore = true)
    @Mapping(target = "requiredActions", ignore = true)
    @Mapping(target = "federatedIdentities", ignore = true)
    @Mapping(target = "socialLinks", ignore = true)
    @Mapping(target = "realmRoles", ignore = true)
    @Mapping(target = "clientRoles", ignore = true)
    @Mapping(target = "clientConsents", ignore = true)
    @Mapping(target = "notBefore", ignore = true)
    @Mapping(target = "federationLink", ignore = true)
    @Mapping(target = "serviceAccountClientId", ignore = true)
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "disableableCredentialTypes", ignore = true)
    @Mapping(target = "access", ignore = true)
    @Mapping(target = "applicationRoles", ignore = true)
    UserRepresentation toUserRepresentation(AuthRequest src);

    default UserRepresentation mapUserRepresentation(AuthRequest src) {
        final var tgt = toUserRepresentation(src);
        final var cred = mapPasswordCredentials(src.getPassword());

        tgt.setCredentials(List.of(cred));
        return tgt;
    }

    @Mapping(target = "value", source = "password")
    @Mapping(target = "temporary", expression = "java(Boolean.FALSE)")
    @Mapping(target = "type", expression = "java(CredentialRepresentation.PASSWORD)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userLabel", ignore = true)
    @Mapping(target = "secretData", ignore = true)
    @Mapping(target = "credentialData", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "config", ignore = true)
    CredentialRepresentation mapPasswordCredentials(String password);
}
