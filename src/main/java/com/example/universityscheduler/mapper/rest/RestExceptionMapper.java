package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.model.ApiResponseError;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(
        imports = {LocalDateTime.class}
)
public interface RestExceptionMapper {

        @Mapping(target = "message", source = "exception.message")
        @Mapping(target = "status", source = "status")
        @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
        ApiResponseError toDto(Exception exception, Integer status);
}
