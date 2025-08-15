package com.leukim.commander.assertions;

import com.leukim.commander.infrastructure.controllers.model.ProductDto;

public class Assertions extends org.assertj.core.api.Assertions {
    public static ProductDtoAssert assertThat(ProductDto actual) {
        return new ProductDtoAssert(actual);
    }
}
