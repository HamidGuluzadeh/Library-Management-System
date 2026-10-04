package com.auspify_tech.library_management_system.mapper;

import com.auspify_tech.library_management_system.dto.request.BookRequest;
import com.auspify_tech.library_management_system.dto.response.BookResponse;
import com.auspify_tech.library_management_system.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    BookResponse mapEntityToResponse(BookEntity bookEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availableCopies", source = "totalCopies")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BookEntity mapRequestToEntity(BookRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availableCopies", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(BookRequest request, @MappingTarget BookEntity entity);

}
