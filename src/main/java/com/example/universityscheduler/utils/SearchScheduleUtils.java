package com.example.universityscheduler.utils;


import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.domain.Teacher;
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

    private static final String TEACHER_TABLE = "teacher";
    private static final String TEACHER_ID = "teacherId";
    private static final String GROUP_ID = "groupId";
    private static final String SUBJECT_ID = "subjectId";
    private static final String UNIVERSITY_ID = "universityId";

    public Specification<Schedule> findAllCompoundSchedules(List<SearchQuery> searchQueries, UUID universityId) {
        return (root, query, criteriaBuilder) -> {
            Join<Schedule, Teacher> teacherJoin = root.join(TEACHER_TABLE, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (SearchQuery searchQuery : searchQueries) {
                if (searchQuery.getSearchType() == SearchType.TEACHER) {
                    predicates.add(criteriaBuilder.equal(root.get(TEACHER_ID), searchQuery.getSearchId()));
                } else if (searchQuery.getSearchType() == SearchType.GROUP) {
                    predicates.add(criteriaBuilder.equal(root.get(GROUP_ID), searchQuery.getSearchId()));
                } else if (searchQuery.getSearchType() == SearchType.SUBJECT) {
                    predicates.add(criteriaBuilder.equal(root.get(SUBJECT_ID), searchQuery.getSearchId()));
                }
            }
            val searchQueryPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            val universityIdPredicate = criteriaBuilder.equal(teacherJoin.get(UNIVERSITY_ID), universityId);
            return criteriaBuilder.and(searchQueryPredicate, universityIdPredicate);
        };
    }
}
