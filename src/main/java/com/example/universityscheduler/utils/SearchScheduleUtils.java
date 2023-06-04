package com.example.universityscheduler.utils;


import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.domain.Room;
import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.domain.University;
import com.example.universityscheduler.model.SearchQuery;
import com.example.universityscheduler.model.SearchType;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.universityscheduler.domain.Schedule.Fields.GROUPS;
import static com.example.universityscheduler.domain.Schedule.Fields.ID;
import static com.example.universityscheduler.domain.Schedule.Fields.ROOM;
import static com.example.universityscheduler.domain.Schedule.Fields.SUBJECT;
import static com.example.universityscheduler.domain.Schedule.Fields.TEACHER;
import static com.example.universityscheduler.domain.Teacher.Fields.UNIVERSITY;

@UtilityClass
public class SearchScheduleUtils {

    public Specification<Schedule> findAllCompoundSchedules(List<SearchQuery> searchQueries, UUID universityId) {
        return (root, query, criteriaBuilder) -> {
            Join<Schedule, Teacher> teacherJoin = root.join(TEACHER, JoinType.LEFT);
            Join<Teacher, University> universityJoin = teacherJoin.join(UNIVERSITY, JoinType.LEFT);
            Join<Schedule, Group> groupJoin = root.join(GROUPS, JoinType.LEFT);
            Join<Schedule, Subject> subjectJoin = root.join(SUBJECT, JoinType.LEFT);
            Join<Schedule, Room> roomJoin = root.join(ROOM, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();

            for (SearchQuery searchQuery : searchQueries) {
                if (searchQuery.getSearchType() == SearchType.TEACHER) {
                    predicates.add(criteriaBuilder.equal(teacherJoin.get(ID), searchQuery.getSearchId()));
                } else if (searchQuery.getSearchType() == SearchType.GROUP) {
                    predicates.add(criteriaBuilder.equal(groupJoin.get(ID), searchQuery.getSearchId()));
                } else if (searchQuery.getSearchType() == SearchType.SUBJECT) {
                    predicates.add(criteriaBuilder.equal(subjectJoin.get(ID), searchQuery.getSearchId()));
                } else if (searchQuery.getSearchType() == SearchType.ROOM) {
                    predicates.add(criteriaBuilder.equal(roomJoin.get(ID), searchQuery.getSearchId()));
                }
            }
            val searchQueryPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            val universityIdPredicate = criteriaBuilder.equal(universityJoin.get(ID), universityId);
            query.distinct(true);
            return criteriaBuilder.and(searchQueryPredicate, universityIdPredicate);
        };
    }
}
