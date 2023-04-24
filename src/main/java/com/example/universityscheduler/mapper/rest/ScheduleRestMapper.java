package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.model.ScheduleExtendedInfo;
import com.example.universityscheduler.model.ScheduleInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(
        uses = {
                GroupRestMapper.class,
                SubjectRestMapper.class,
                TeacherRestMapper.class
        }
)
public abstract class ScheduleRestMapper {

    @Autowired
    private GroupRestMapper groupRestMapper;

    @Mapping(target = "groups", source = "groups", qualifiedByName = "toGroups")
    @Mapping(target = "subject", source = "subjectId")
    @Mapping(target = "teacher", source = "teacherId")
    public abstract Schedule toEntity(ScheduleInfo scheduleInfo);
    @Mapping(target = "groups", source = "groups", qualifiedByName = "toGroupIds")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    public abstract ScheduleInfo toDto(Schedule schedule);

    @Mapping(target = "groups", source = "groups", qualifiedByName = "toGroupIds")
    @Mapping(target = "subjectName", source = "subject.title")
    @Mapping(target = "teacherName", source = "teacher.fullName")
    public abstract ScheduleExtendedInfo toExtendedDto(Schedule schedule);

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
