package com.leukim.commander.infrastructure.mappers;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import com.leukim.commander.infrastructure.adapters.out.model.DbProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper extends EntityMapper<ProductDto, Product, DbProduct> {
    @Mapping(target = "id", ignore = true)
    Product create(CreateProductDto createProductDto);
}
