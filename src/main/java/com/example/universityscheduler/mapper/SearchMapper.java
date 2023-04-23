package com.example.universityscheduler.mapper;

import com.example.universityscheduler.model.SearchQuery;
import com.example.universityscheduler.model.SearchType;
import org.mapstruct.Mapper;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface SearchMapper {

    default Optional<SearchQuery> toSearchQuery(Optional<UUID> searchId, Optional<SearchType> searchType) {
        SearchQuery searchQuery = new SearchQuery();
        if(searchId.isPresent() && searchType.isPresent()) {
            searchQuery.setSearchId(searchId.get());
            searchQuery.setSearchType(searchType.get());
            return Optional.of(searchQuery);
        }
        return Optional.empty();
    }
}
