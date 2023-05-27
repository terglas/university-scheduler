package com.example.universityscheduler.utils;


import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.model.SearchQuery;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@UtilityClass
public class SearchScheduleUtils {

    private static final String SEARCH_ID = "searchId";
    private static final String SEARCH_TYPE = "searchType";

    public Specification<Schedule> findAllCompoundSchedules(List<SearchQuery> searchQueries) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            for (SearchQuery searchQuery : searchQueries) {
                Predicate searchPredicate = getSearchPredicate(searchQuery, root, criteriaBuilder);
                predicate = criteriaBuilder.or(predicate, searchPredicate);
            }
            return predicate;
        };
    }

    private Predicate getSearchPredicate(SearchQuery searchQuery, Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        Path<Object> searchIdPath = root.get(SEARCH_ID);
        Path<Object> searchTypePath = root.get(SEARCH_TYPE);

        Predicate searchIdPredicate = criteriaBuilder.equal(searchIdPath, searchQuery.getSearchId());
        Predicate searchTypePredicate = criteriaBuilder.equal(searchTypePath, searchQuery.getSearchType());

        return criteriaBuilder.and(searchIdPredicate, searchTypePredicate);
    }
}
