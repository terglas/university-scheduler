package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.RoomzApi;
import com.example.universityscheduler.mapper.rest.RoomRestMapper;
import com.example.universityscheduler.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomzApi {

    private final RoomService roomService;
    private final RoomRestMapper roomRestMapper;

}
