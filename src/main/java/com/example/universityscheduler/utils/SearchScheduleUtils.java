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

@UtilityClass
public class SearchScheduleUtils {
    // TODO implement through metamodel
    private static final String TEACHER_FIELD = "teacher";
    private static final String GROUPS_FIELD = "groups";
    private static final String SUBJECT_FIELD = "subject";
    private static final String ROOM_FIELD = "room";
    private static final String UNIVERSITY_FIELD = "university";
    private static final String ID = "id";

    public Specification<Schedule> findAllCompoundSchedules(List<SearchQuery> searchQueries, UUID universityId) {
        return (root, query, criteriaBuilder) -> {
            Join<Schedule, Teacher> teacherJoin = root.join(TEACHER_FIELD, JoinType.LEFT);
            Join<Teacher, University> universityJoin = teacherJoin.join(UNIVERSITY_FIELD, JoinType.LEFT);
            Join<Schedule, Group> groupJoin = root.join(GROUPS_FIELD, JoinType.LEFT);
            Join<Schedule, Subject> subjectJoin = root.join(SUBJECT_FIELD, JoinType.LEFT);
            Join<Schedule, Room> roomJoin = root.join(ROOM_FIELD, JoinType.LEFT);
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
            return criteriaBuilder.and(searchQueryPredicate, universityIdPredicate);
        };
    }
}
