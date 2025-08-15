package com.leukim.commander.assertions;

import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import org.assertj.core.api.AbstractAssert;

import java.util.UUID;

public class ProductDtoAssert extends AbstractAssert<ProductDtoAssert, ProductDto> {
    public ProductDtoAssert(ProductDto actual) {
        super(actual, ProductDtoAssert.class);
    }

    public ProductDtoAssert hasId(UUID expectedId) {
        isNotNull();
        Assertions.assertThat(actual.id()).isEqualTo(expectedId);
        return this;
    }

    public ProductDtoAssert hasName(String expectedName) {
        isNotNull();
        Assertions.assertThat(actual.name()).isEqualTo(expectedName);
        return this;
    }

    public ProductDtoAssert hasDescription(String expectedDescription) {
        isNotNull();
        Assertions.assertThat(actual.description()).isEqualTo(expectedDescription);
        return this;
    }
}

