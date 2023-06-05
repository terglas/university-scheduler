package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.domain.Notification;
import com.example.universityscheduler.model.NotificationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(
        uses = {
                GroupRestMapper.class
        }
)
public abstract class NotificationRestMapper {

    @Autowired
    private GroupRestMapper groupRestMapper;

    @Mapping(target = "groups", source = "groups", qualifiedByName = "toGroups")
    public abstract Notification toEntity(NotificationEvent notificationEvent);

    @Mapping(target = "groups", source = "groups", qualifiedByName = "toGroupIds")
    public abstract NotificationEvent toDto(Notification notification);

    @Named("toGroups")
    protected List<Group> toGroups(List<UUID> groupIds) {
        return groupIds.stream()
                .map(groupRestMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Named("toGroupIds")
    protected List<UUID> toGroupIds(List<Group> groups) {
        return groups.stream()
                .map(Group::getId)
                .collect(Collectors.toList());
    }
}
