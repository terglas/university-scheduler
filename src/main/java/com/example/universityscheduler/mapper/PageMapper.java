package com.example.universityscheduler.mapper;

import com.example.universityscheduler.model.PageParams;
import lombok.val;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper
public interface PageMapper {

    default PageParams toDto(Optional<PageParams> pageParams) {
        val params = pageParams.orElse(new PageParams());
        val pageCurrent = Optional.ofNullable(params.getPageCurrent()).orElse(1);
        val pageSize = Optional.ofNullable(params.getPageSize()).orElse(50);
        val pageTotal = Optional.ofNullable(params.getPageTotal()).orElse(1);
        return new PageParams()
                .pageCurrent(pageCurrent)
                .pageSize(pageSize)
                .pageTotal(pageTotal);
    }
}
