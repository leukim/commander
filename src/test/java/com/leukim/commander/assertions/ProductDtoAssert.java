package com.leukim.commander.assertions;

import static com.leukim.commander.assertions.Assertions.assertThat;

import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import java.util.UUID;
import org.assertj.core.api.AbstractAssert;

public class ProductDtoAssert
    extends AbstractAssert<ProductDtoAssert, ProductDto> {
    public ProductDtoAssert(ProductDto actual) {
        super(actual, ProductDtoAssert.class);
    }

    public ProductDtoAssert hasId(UUID expectedId) {
        isNotNull();
        assertThat(actual.id()).isEqualTo(expectedId);
        return this;
    }

    public ProductDtoAssert hasName(String expectedName) {
        isNotNull();
        assertThat(actual.name()).isEqualTo(expectedName);
        return this;
    }

    public ProductDtoAssert hasDescription(String expectedDescription) {
        isNotNull();
        assertThat(actual.description()).isEqualTo(expectedDescription);
        return this;
    }
}

