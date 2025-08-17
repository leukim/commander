package com.leukim.commander.infrastructure.mappers;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.infrastructure.adapters.out.model.DbProduct;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper extends DtoMapper<ProductDto, Product> {
    @Mapping(target = "id", ignore = true)
    Product create(CreateProductDto createProductDto);

    Product fromDbModel(DbProduct dbProduct);

    @Mapping(target = "quantities", ignore = true)
    DbProduct toDbModel(Product product);
}
