package com.example.universityscheduler.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TeacherDTO {

    private UUID id;
    private String name;
}
