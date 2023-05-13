package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.model.AuthRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public abstract class AuthMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", source = "password", qualifiedByName = "encode")
    public abstract UserAccount map(AuthRequest src);

    public abstract AuthRequest toLoginRequest(String email, String password);

    @Named("encode")
    protected String encode(String password) {
        return passwordEncoder.encode(password);
    }

}
