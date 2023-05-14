package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserAccountRestMapper {

    @Mapping(target = "universityId", source = "university.id")
    UserInfo toDto(UserAccount userAccount);
}
