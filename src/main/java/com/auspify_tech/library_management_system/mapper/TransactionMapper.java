package com.auspify_tech.library_management_system.mapper;

import com.auspify_tech.library_management_system.dto.response.TransactionResponse;
import com.auspify_tech.library_management_system.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    TransactionResponse mapEntityToResponse(TransactionEntity transactionEntity);

}
