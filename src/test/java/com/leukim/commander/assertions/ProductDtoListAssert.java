package com.leukim.commander.assertions;

import static com.leukim.commander.assertions.Assertions.assertThat;

import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import java.util.List;
import org.assertj.core.api.AbstractAssert;

public final class ProductDtoListAssert extends AbstractAssert<ProductDtoListAssert, List<ProductDto>> {
    ProductDtoListAssert(List<ProductDto> productDtos) {
        super(productDtos, ProductDtoListAssert.class);
    }

    public ProductDtoListAssert containsProductData(List<List<String>> productData) {
        List<List<String>> actualData =
            actual.stream().map(productDto -> List.of(productDto.name(), productDto.description())).toList();

        assertThat(actualData).isEqualTo(productData);

        return this;
    }
}
